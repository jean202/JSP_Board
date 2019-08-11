<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lec.beans.*" %>
<%
	// dao사용한 트랜잭션 정의
	WriteDTO [] arr = (WriteDTO[])request.getAttribute("list");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>자유게시판</title>
<style>

</style>
<link href="Css/freelist.css" rel="stylesheet" type="text/css">
</head>
<body>
		<hr>
		<h2>자유게시판</h2>
<%
	if(arr == null || arr.length == 0){
		out.println("데이터가 없습니다<br>");
	} else {
%>
		<table id = "freelist">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>
<%
		for(int i = 0; i < arr.length; i++){
			out.println("<td>" + arr[i].getId() + "</td>");
			out.println("<td><a href='view.do?write_id=" + arr[i].getId() + "'>" + arr[i].getSubject() + "</a></td>");
			out.println("<td>" + arr[i].getAcc_num() + "</td>"); 
			out.println("<td>" + arr[i].getViewCnt() + "</td>"); 
			out.println("<td>" + arr[i].getRegDate() + "</td>");
			out.println("</tr>");
		}// end for
		
%>
		</table>	
<%
		}//end if
%>	
		<br>
		<button id = "write" onclick="location.href = 'write.do'">글쓰기</button>


</body>
</html>