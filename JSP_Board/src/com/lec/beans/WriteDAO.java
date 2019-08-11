package com.lec.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// import java.sql.* 한다음 ctrl shift o 눌러주면 알아서 정리해 준다
import com.lec.jsp.D;

public class WriteDAO {
	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;

	// DAO 객체가 생성될때 Connection 도 생성된다
	public WriteDAO() {
		try {
			Class.forName(D.DRIVER);
			conn = DriverManager.getConnection(D.URL, D.USERID, D.USERPW);
			System.out.println("WriteDAO생성, 데이터베이스 연결!!");
		} catch (Exception e) {
			e.printStackTrace();
		} // end try
	}// 생성자

	// DB 자원 반납 메소드, 만들어놓으면 편함..
	public void close() throws SQLException {
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	} // end close();

	// 새 글 작성하는 메소드 (DTO로부터)
	public int insert(WriteDTO dto) throws SQLException {
		String subject = dto.getSubject();
		String content = dto.getContent();
		String name = dto.getName();
		String fileSystemName = dto.getFileSystemName();
		String originalFileName = dto.getOriginalFileName();
		
		
		int cnt = this.insert(subject, content, name, fileSystemName, originalFileName);
		return cnt;
	}

	// dao.insert(subject, content, name, fileSystemName, originalFileName);
	// 새 글 작성(제목, 내용, 작성자 정보 파일 이름 2개 받아서 )
	public int insert(String subject, String content, String name,  String fileSystemName, String originalFileName/*, String fileSystemName2, String originalFileName2*/)
			throws SQLException {
		int cnt = 0;
		int acc_num = 0;
		try {
			// 여러 쿼리문을 하나의 트랜잭션으로 처리하기
			conn.setAutoCommit(false); // 일단 auto-commit 을 false 로 설정

			// 작성자 이름으로 작성자 계정 번호 가져오는 쿼리
			// SELECT_ACC_NUM = "select acc_num from account where name = ?";
			pstmt = conn.prepareStatement(D.SELECT_ACC_NUM);
			pstmt.setString(1, name); // 작성자 이름
			rs = pstmt.executeQuery();

			while (rs.next()) {
				acc_num = rs.getInt("acc_num");
			} // end while

			pstmt.close();

			// 작성자 번호를 받아서 freeboard테이블에 레코드 입력하는 쿼리
			pstmt = conn.prepareStatement(D.FREE_WRITE_INSERT);
			pstmt.setString(1, subject); // 게시판 제목
			pstmt.setString(2, content); // 글 내용
			pstmt.setInt(3, acc_num); // 글 작성자 번호

			System.out.println(subject + content + acc_num);
			/*
			 * "INSERT INTO freeboard" +
			 * "(write_id, subject, content, regdate, viewcnt, board_id, acc_num) " +
			 * "VALUES" + "(freeboard_seq.nextval, ?, ?, sysdate, 0, 2, ?)";
			 */

			// insert into freeboard values(freeboard_seq.nextval,'a','a',sysdate,0,2,1);
			pstmt.executeUpdate();
			// ※cnt 결과값에 따른 처리 필요

			// 파일 이름 2개를 파일 테이블에 입력하는 쿼리
			// insert into file_info values(file_info_seq.nextval, ?, ?, ?);			
			pstmt = conn.prepareStatement(D.FILE_INFO_INSERT);
			pstmt.setString(1, fileSystemName); // 서버에 저장된 파일 네임 전달
			pstmt.setString(2, originalFileName);
			pstmt.setInt(3, 1); // ※ 사용자가 업로드 하는 순서대로 자동으로 시퀀스가 넘어갈 수 있도록 처리해야 한다
			cnt = pstmt.executeUpdate();
					
			/*
			 * insert into file_info values(file_info_seq.nextval, fileSystemName,
			 * originalFileName, 1, 2);	 * 
			 * 
			 * 
			 */

			conn.commit(); // 트랜잭션 성공

		} catch (SQLException e) {
			conn.rollback(); // 예외 발생하면 roll back
			throw e; // 다시 발생된 예외을 throw
		} finally {
			close();
		}
		return cnt;
	}// end insert();

	// ResultSet을 DTO의 배열로 리턴
	public WriteDTO[] createArray(ResultSet rs) throws SQLException {
		ArrayList<WriteDTO> list = new ArrayList<WriteDTO>();

		while (rs.next()) {
			int write_id = rs.getInt("write_id");
			String subject = rs.getString("subject");
			String content = rs.getString("content");
			if (content == null)
				content = ""; // null처리 해주기(내용을 안썼으면 빈화면 나오도록)
			// String name = rs.getString("name");
			Date d = rs.getDate("regdate");
			Time t = rs.getTime("regdate");
			String regdate = "";
			if (d != null) {
				regdate = new SimpleDateFormat("yyyy-MM-dd").format(d) + " "
						+ new SimpleDateFormat("hh:mm:ss").format(t);
			}
			int viewCnt = rs.getInt("viewcnt");
			int board_id = rs.getInt("board_id");
			int acc_num = rs.getInt("acc_num");

			WriteDTO dto = new WriteDTO(write_id, subject, content, viewCnt, acc_num);
			dto.setRegDate(regdate);
			list.add(dto);
		} // end while

		int size = list.size();
		WriteDTO[] arr = new WriteDTO[size];
		list.toArray(arr); // 리스트에 저장된 데이터를 배열객체에 복사
		return arr;
	}// end createArray()

	
	/*freeboard에서 write_id로 파일 id를 통해 파일 이름 까지 select
	 * 
	 * select fi.file_id, fi.systemname, fi.originalname, fb.subject from file_info
	 * fi, freeboard fb WHERE fi.file_id = fb.file_id AND write_id=28;
	*/
	// 전체 SELECT하는 메소드
	public WriteDTO[] select() throws SQLException {
		WriteDTO[] arr = null;

		try {
			conn.setAutoCommit(false); // 일단 auto-commit 을 false 로 설정
			pstmt = conn.prepareStatement(D.FREE_WRITE_SELECT);
			rs = pstmt.executeQuery();
			arr = createArray(rs);
		} finally {
			close();
		}
		return arr;
	} // end select()

	// 특정 id의 글만 SELECT
	public WriteDTO[] selectById(int write_id) throws SQLException {
		WriteDTO arr[] = null;
		try {
			pstmt = conn.prepareStatement(D.FREE_WRITE_SELECT_BY_ID); // 조회수 증가 안시키고 읽어만 오는 쿼리
			pstmt.setInt(1, write_id);
			rs = pstmt.executeQuery();
			arr = createArray(rs);
		} finally {
			close();
		}
		return arr;
	}

	// 특정 id 글 내용 읽고, 조회수 증가
	// viewcnt 도 1증가해야 하고, 읽어와야 한다 --> 트랜잭션 처리
	public WriteDTO[] readById(int write_id) throws SQLException {
		int cnt = 0;
		WriteDTO[] arr = null;

		try {
			// 여러 쿼리문을 하나의 트랜잭션으로 처리하기
			conn.setAutoCommit(false); // 일단 auto-commit 을 false 로 설정

			// 조회수 증가 쿼리
			pstmt = conn.prepareStatement(D.FREE_WRITE_INC_VIEWCNT);
			pstmt.setInt(1, write_id);
			cnt = pstmt.executeUpdate();
			// ※cnt 결과값에 따른 처리 필요

			pstmt.close();

			// 글 읽어오기 쿼리
			pstmt = conn.prepareStatement(D.FREE_WRITE_SELECT_BY_ID);
			pstmt.setInt(1, write_id);
			rs = pstmt.executeQuery();

			arr = createArray(rs);
			conn.commit(); // 트랜잭션 성공

		} catch (SQLException e) {
			conn.rollback(); // 예외 발생하면 roll back
			throw e; // 다시 발생된 예외을 throw
		} finally {
			close();
		}
		return arr;
	} // end readById()

	// 특정 id의 글 수정(제목,내용)
	public int update(int write_id, String subject, String content) throws SQLException {
		int cnt = 0;
		try {
			pstmt = conn.prepareStatement(D.FREE_WRITE_UPDATE);
			pstmt.setString(1, subject); // 게시판 제목
			pstmt.setString(2, content); // 글 내용
			pstmt.setInt(3, write_id); // 글 id
			cnt = pstmt.executeUpdate();
		} finally {
			close();
		}
		return cnt;
	} // end update()

	// 특정 id의 글을 삭제하는 DAO메서드
	public int deleteById(int write_id) throws SQLException {
		int cnt = 0;
		try {
			pstmt = conn.prepareStatement(D.FREE_WRITE_DELETE_BY_ID);
			pstmt.setInt(1, write_id);
			cnt = pstmt.executeUpdate();
		} finally {
			close();
		}
		return cnt;
	} // end delteById()
}
