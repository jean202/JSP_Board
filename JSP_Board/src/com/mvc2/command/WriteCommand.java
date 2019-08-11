package com.mvc2.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.beans.WriteDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class WriteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int cnt = 0;
		WriteDAO dao = new WriteDAO();
		String saveDirectory = "C:\\tomcat\\ckupload"; // 이스케이프 시퀀스 때문에 역슬래시 그대로 출력하려면 두개 써줘야 함
		// C:\tomcat\apache-tomcat-8.5.43\wtpwebapps그동안 이곳의 파일들이 run 되고 있었음
		// 웹컨텐트의 내용이 바로 서버에 올라가는 것이다 -> 이미지도 이 곳에 두어야 함

		ServletContext context = request.getSession().getServletContext();

		// C:\tomcat\apache-tomcat-8.5.43\wtpwebapps\JSP20_FileUpload 이 경로를 뽑기 위해
		// getServletContext()를 통해 가져온 것
		// jspMain2-context라고 부름
		// 서버에서(서블릿) 어디 어느 폴더에서 서블릿으로 변환되었는지 여부를 알 수 있다.
		saveDirectory = context.getRealPath("ckupload"); // 위의 saveDirectory를 덮어쓴다
		System.out.println("업로드 경로: " + saveDirectory);
		int maxPostSize = 5 * 1024 * 1024;// 최대 5M
		String encoding = "utf-8"; // response인코딩

		
		FileRenamePolicy policy = new DefaultFileRenamePolicy();
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, // JSP내부 객체인 request가 들어가야 한다
					saveDirectory, // 저장경로
					maxPostSize, // 업로드 파일 최대 크기
					encoding, policy // rename policy
			);
			Enumeration names = null;
			// 1. Parameter 파트- name들 추출
			String name = multi.getParameter("name");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			
			System.out.println(multi);
			
			String originalFileName = multi.getOriginalFileName("file1");
			System.out.println("원본 파일 이름 : " + originalFileName + "<br>");
			
			String fileSystemName = multi.getFilesystemName("file1");
			System.out.println("파일시스템이름: " + fileSystemName );
			/*
			String originalFileName2 = multi.getOriginalFileName("file2");
			System.out.println("원본 파일 이름 : " + originalFileName2 + "<br>");
			
			String fileSystemName2 = multi.getFilesystemName("file2");
			System.out.println("파일시스템이름: " + fileSystemName2 );
			 */
			
			// 유효성 체크
			cnt = dao.insert(subject, content, name, fileSystemName, originalFileName);
			// insert의 결과물은 정수
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("result", cnt); // DML 결과값 담음
	}

}
