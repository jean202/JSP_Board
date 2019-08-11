package com.controller.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc2.command.AjaxListCommand;
import com.mvc2.command.Command;
import com.mvc2.command.DeleteCommand;
import com.mvc2.command.ListCommand;
import com.mvc2.command.SelectCommand;
import com.mvc2.command.UpdateCommand;
import com.mvc2.command.ViewCommand;
import com.mvc2.command.WriteCommand;

@WebServlet("*.ajax")
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
    public AjaxController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ajaxAction(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ajaxAction(request,response);
	}
	protected void ajaxAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ajaxAction()");
		request.setCharacterEncoding("utf-8");// 컨트롤러는 아래 두가지 정보를 갖고 움직여야 한다.
		String viewPage = null;  // 어떠한 페이지에 보여줄지 (View)
		Command command = null;   // 어떠한 로직을 수행할지
		
		// URL 로부터 URI, ContextPath, Command 분리
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length()); // URL에서 커맨드 분리
		// conPath(컨텍스트 패스) 뒤에 있는것만 분리 하라는 뜻
		
		// 테스트 출력
		System.out.println("uri: " + uri);
		System.out.println("conPath: " + conPath);
		System.out.println("com: " + com);
		
		// 컨트롤러는 커맨드에 따라 로직을 수행하고 결과를 내보낼 뷰를 결정한다
		switch(com) {
		case "/list.ajax":
			new ListCommand().execute(request, response);
			new AjaxListCommand().execute(request,response);
			break;
		} // end switch
		
		// forwarding 필요없음(ajax는 response안함)
	}
}
