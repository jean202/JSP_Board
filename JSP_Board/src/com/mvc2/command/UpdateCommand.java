package com.mvc2.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.beans.WriteDAO;

public class UpdateCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		// ※ 유효성 체크해야
		WriteDAO dao = new WriteDAO();
		int cnt = 0;
		
		try {
			cnt = dao.update(id, subject, content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("result", cnt);
	}

}
