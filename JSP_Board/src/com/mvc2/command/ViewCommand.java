package com.mvc2.command;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.beans.WriteDTO;
import com.lec.beans.WriteDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;


public class ViewCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WriteDTO [] arr = null;
		// ※원래는 이부분에서 매개변수 검증이 필요함(try catch도 없고 id에 숫자 아닌값 들어오면 뻗어버림)
		System.out.println(">>>>>>>> write_id : " + request.getParameter("write_id"));
		int write_id = Integer.parseInt(request.getParameter("write_id"));
		WriteDAO dao = new WriteDAO();
		
		
		String fileSystemName = request.getParameter("fileSystemName");
		if(fileSystemName == null && !"".equals(fileSystemName)){return;}
		String originalFileName = request.getParameter("originalFileName");
		if(originalFileName == null && !"".equals(originalFileName)){originalFileName = fileSystemName;}
	
		// 파일의 유형파악
		//	- FileInputStream : byte stream - 바이너리를 다룰 때 쓰는 스트림
		//	- FileReader : char stream - 문자 기반
		
		// 1) 다운로드 받을 파일의 경로 및 이름을 지정하기
		// 		업로드된 폴더의 위치는 알고 있어야 함
		String realPath;
		// realPath = "C:\\tomcat\\ckupload";
		realPath = "C:\\tomcat\\apache-tomcat-8.5.43\\wtpwebapps\\JSP_Board\\ckupload";
		ServletContext context = request.getSession().getServletContext();
		//realPath = context.getRealPath("ckupload");
		String downloadFilePath = realPath + File.separator + fileSystemName;
		System.out.println("downloadFilePath: " + downloadFilePath);
			
		// 2) 유형(MIME) 확인 - 읽어올 파일의 유형
		// 	-> response받을 브라우저에서 페이지 생성할 때 위 타입 정보로 판단함
		String fileType;
		fileType = "application/octet-stream";  // 8bit 스트림 - 유형이 알려지지 않은 파일에 대한 읽기 형식을 지정해 줄 때 쓰인다
		System.out.println("파일유형(MIME): " + fileType);
		
		// 3) response 세팅
		//	  response에 유형(MIME)을 알려준다
		//	    원본파일 이름 세팅
		//    response 헤더 세팅 (setHeader() 사용)
		response.setContentType(fileType);	// 이전에 HTMl을 response 할때는 text/html;이었음
		// 원본파일의 이름도 URL encoding 해줘야 한다
		String enc = "utf-8";
		String encFileName = URLEncoder.encode(originalFileName, enc); // 원본파일의 이름으로 다운받을수 있도록 사전에 인코딩 처리
		response.setHeader("Content-Disposition", "attachment; filename=" + encFileName);
		
		// 4) 클라이언트에 파일 전송하기 전에, 내부객체 out을 clear() - 불법주차된 차들 치워주기
		/*
		 * close(); // 출력할 내용들을 제거 out = pageContext.pushBody();
		 */	// <-- 커스텀 태그 등도 clear하고 JspWriter(out)에 새로운 body 생성
		
		// 5) File 전송 --> OutputStream중에서 ServletOutputStream 을 전송하기
		//    전송 마무리 후 flush()해주기 - close()
		File srcFile = new File(downloadFilePath);
		FileInputStream in = new FileInputStream(srcFile);
		ServletOutputStream sout = response.getOutputStream();
		
		// 읽어야할 최대용량만큼의 버퍼 준비
		byte [] buff = new byte[5 * 1024 * 1024];	// 5M 버퍼 준비
		int numRead = 0;
		int totalRead = 0;
		// in에서 읽어서 sout으로 뽑기
		try{
			
			arr = dao.readById(write_id); 	// 읽기 + 조회수 증가 다 설정되어있는 트랜잭션
			request.setAttribute("list", arr); // ★★SELECT쿼리 결과가 담긴다 -> 컨트롤러에 넘겨주기
			
			// 파일로부터 읽어서 response 해주기
					// 읽어들인 바이트 수 리턴	
			while((numRead = in.read(buff, 0, buff.length)) != -1){
				totalRead += numRead;
				sout.write(buff, 0, numRead); // 읽어서 클라이언트에게 전송
			}		
			
			
		}catch(Exception e){
			// 파일 다운로드하기 창에서 '취소'를 누르면 IOException으로 넘어온다
			e.printStackTrace();
		}finally{
			sout.flush();	// OutputStream에서 한방울까지 다 짜내기 : 완전히 전송해야 한다	
			sout.close();
			in.close();
		}
		System.out.println("전송byte : " + totalRead + " bytes");
	
		
		
		
	}

}
