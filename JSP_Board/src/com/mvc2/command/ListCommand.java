package com.mvc2.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.beans.WriteDAO;
import com.lec.beans.WriteDTO;

public class ListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		WriteDAO dao = new WriteDAO();	// DAO객체 생성
		WriteDTO arr[] = null;
		
		try{
			// 트랜잭션 수행
			arr = dao.select();
			// "list"라는 name으로 request에 arr값 저장
			// 즉 request에 담아서 컨트롤러에 전달 -> execute를 컨트롤러가 호출할 때 컨트롤러의 리퀘스트 바구니에 담아만 놓으면 된다, 바구니는 원래 컨트롤러것
			request.setAttribute("list", arr);
		}catch(SQLException e) {
			// 만약 ConnectionPool을 사용한다면 
			// 여기서 NamingException 도 catch 해주어야 한다.
			e.printStackTrace();
		}
	}

}
