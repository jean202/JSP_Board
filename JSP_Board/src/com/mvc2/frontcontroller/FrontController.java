package com.mvc2.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc2.command.Command;
import com.mvc2.command.DeleteCommand;
import com.mvc2.command.ListCommand;
import com.mvc2.command.SelectCommand;
import com.mvc2.command.UpdateCommand;
import com.mvc2.command.ViewCommand;
import com.mvc2.command.WriteCommand;


@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo() 호출");
		
		request.setCharacterEncoding("UTF-8");
		// 컨트롤러는 아래 두가지 정보를 갖고 움직여야 한다.
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
		try {
			switch(com) {			
			case "/list.do":
				command = new ListCommand();
				command.execute(request, response);
				viewPage = "list.jsp";
				break;
			case "/write.do":
				viewPage = "write.jsp";
				break;
			case "/writeOk.do":
				command = new WriteCommand();
				command.execute(request, response);
				viewPage = "writeOk.jsp";
				break;
			case "/view.do":
				command = new ViewCommand();
				command.execute(request, response);
				viewPage = "view.jsp";
				break;
			case "/update.do":
				command = new SelectCommand();
				command.execute(request, response);
				viewPage = "update.jsp";
				break;
			case "/updateOk.do":
				command = new UpdateCommand();
				command.execute(request, response);
				viewPage = "updateOk.jsp";
				break;
			case "/deleteOk.do":
				command = new DeleteCommand();
				command.execute(request, response);
				viewPage = "deleteOk.jsp";
				break;
			case "/FileDownload.do":
				command = new DeleteCommand();
				command.execute(request, response);
				viewPage = "FileDownload.jsp";
				break;
			} // end switch
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// response를 위해서 위에서 결정된 viewPage로 forwarding 해주기
		if(viewPage != null) {
			// 포워딩 해주는 방법-서블릿에서 포워딩이 가능			
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		
	}
	
} // end Controller
