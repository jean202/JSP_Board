package com.mvc2.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lec.beans.WriteDTO;

public class AjaxListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String reqType = (String)request.getParameter("reqType");
		if(reqType == null) reqType = "json";
		// "xml"혹은 "json"으로 response하기
		switch(reqType) {
		case "xml":
			responseXML(request, response);
			break;
		case "json":
			responseJSON(request, response);
			break;
		default:
			responseJSON(request, response);
			break;
		}
	}// end execute()
	
	private void responseJSON(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonOutput = new JSONObject(); // 최종결과는 object(자바스크립트)
		// 자바스크립트로 { }비어있는 오브젝트 하나 만든 것
		// 데이터 개수 알 수 있다(글 목록 개수) -> 리스트에 담겨 있다
		WriteDTO [] dtoArr = (WriteDTO[])request.getAttribute("list");
		int count = (dtoArr == null) ? 0 : dtoArr.length;
		jsonOutput.put("count", count);
		
		// 데이터 목록
		JSONArray dataArr = new JSONArray();	// array(빈 배열)
		for (int i = 0; i < count; i++) {
			JSONObject dataObj = new JSONObject();
			
			dataObj.put("id", dtoArr[i].getId());
			dataObj.put("name", dtoArr[i].getName());
			dataObj.put("subject", dtoArr[i].getSubject());
			dataObj.put("content", dtoArr[i].getContent());
			dataObj.put("viewcnt", dtoArr[i].getViewCnt());
			dataObj.put("regdate", dtoArr[i].getRegDate());
			
			// array에 추가
			dataArr.put(dataObj);
		}
		jsonOutput.put("datalist", dataArr);
		
		try {
			String jsonString = jsonOutput.toString();	// JSON object를 문자열로 변환
			response.setContentType("application/json; charset=utf-8");	// MIME 설정
			response.getWriter().write(jsonString);	// response보내기
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	private void responseXML(HttpServletRequest request, HttpServletResponse response) {
		
	}

}
