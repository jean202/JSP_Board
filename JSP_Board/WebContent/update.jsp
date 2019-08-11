<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lec.beans.*" %>

<%// Controller로부터 결과 데이터를 받아오기
	WriteDTO[] arr = (WriteDTO[])request.getAttribute("list");
%>

<%
	if (arr == null || arr.length == 0) {
%>

<script>
			alert("해당 글은 삭제되거나 없습니다");
			history.back();
		</script>
<%
	return;
	} // end if
%>
<%
	int id = arr[0].getId();
	String name = "" + arr[0].getName();
	String subject = arr[0].getSubject();
	String content = arr[0].getContent();
	String regdate = arr[0].getRegDate();
	int viewcnt = arr[0].getViewCnt();
%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>수정하기<%= subject %></title>
</head>
<script>
// form 검증(validation)
// '작성자' 입력 필수, '제목' 입력 필수
function chkSubmit(){
	var frm = document.forms["frm"];
	var subject = frm["subject"].value.trim();
	if(subject == ""){
		alert("제목은 반드시 작성해야 합니다");
		frm["subject"].focus();
		return false;
	}
	return true;
}
</script>
<body>
<h2>수정<%= subject %></h2>
<%-- 글 내용이 많을수 있기 때문에 POST 방식 사용 --%>
<form name="frm" action="updateOk.do" method="post" onsubmit="return chkSubmit()">
<input type="hidden" name="id" value="<%=id %>"/>
작성자:<%= name %>"<br><%--작성자 이름은 변경불가--%>
제목:<input type="text" name="subject" value="<%=subject%>"/><br>
내용:<br>
<textarea name="content"><%= content %></textarea>
<br><br>
<input type="submit" value="수정"/>
</form>
<br>
<button type="button" onclick="history.back();">이전으로</button>
<button type="button" onclick="location.href='list.do'">목록으로</button>
</body>
</html>