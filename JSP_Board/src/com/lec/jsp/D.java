package com.lec.jsp;
/*
 * DB접속 정보, 쿼리문, 테이블명, 컬럼명 등은
 * 별도로 관리하든지
 * XML 등에서 관리하는 것이 좋다.
 */
public class D {
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	public static final String USERID = "scott5";
	public static final String USERPW = "tiger5";
	
	// freeboard의 게시글 관련 쿼리문
	
	// 작성자 이름으로 작성번호 받아오는 쿼리문
	public static final String SELECT_ACC_NUM = 
			"select acc_num from account where name = ?";
	
	public static final String FREE_WRITE_INSERT = 
			"INSERT INTO freeboard "
			+ "(write_id, subject, content, regdate, viewcnt, acc_num, file_id) "
			+ "VALUES "
			+ "(freeboard_seq.nextval, ?, ?, sysdate, 0, ?, file_id_seq.nextval)"; 
			
	// + "(select acc_num from account where name = '?'))";
	/*
	 * INSERT INTO freeboard(write_id, subject, content, regdate, viewcnt,  board_id, acc_num) 
	 * VALUES(freeboard_seq.nextval, 'test1', 'test1', sysdate ,0, 2 , 1);	
	  이거는 된다
	 */
	public static final String FREE_WRITE_SELECT = 
			"SELECT * FROM freeboard ORDER BY write_id DESC";
	
	public static final String FREE_WRITE_SELECT_BY_ID = 
			"SELECT * FROM freeboard WHERE write_id=?";
	
	public static final String FREE_WRITE_INC_VIEWCNT = 
			"UPDATE freeboard SET viewcnt = viewcnt + 1 WHERE id = ?";
	
	public static final String FREE_WRITE_DELETE_BY_ID = 
			"DELETE FROM freeboard WHERE write_id = ?";

	public static final String FREE_WRITE_UPDATE =
			"UPDATE freeboard SET subject = ?, content = ? WHERE write_id = ?";
	
	// file_info테이블에 insert하는 쿼리
	// insert into file_info values(file_info_seq.nextval, ?, ?, ?);
	public static final String FILE_INFO_INSERT = "INSERT INTO file_info VALUES(file_info_seq.nextval, ?, ?, ?)";
		
	
	// write_id를 가지고 해당 write_id에 대한 file_id, 시스템네임, 오리지날네임을 select 하는 쿼리문
	public static final String FREE_INFO_SELECT = "select fi.file_id, fi.systemname, "
			+ "fi.originalname file_info fi, " 
	+  "freeboard fb WHERE fi.file_id = fb.file_id AND write_id = ?";
			
	
} // end D


