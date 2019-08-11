package com.mvc2.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.beans.WriteDAO;
import com.lec.beans.WriteDTO;

public class SelectCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		WriteDTO [] arr = null;
		WriteDAO dao = new WriteDAO();
		try {
			arr = dao.selectById(id); // 읽기only
			request.setAttribute("list", arr);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
