<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- cos 라이브러리 --%>
<%@ page import = "com.oreilly.servlet.MultipartRequest" %>
<%@ page import = "com.oreilly.servlet.multipart.FileRenamePolicy" %>
<%@ page import = "com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%-- parameter 값들, file 값들 추출 --%>
<%@ page import="java.util.Enumeration" %>
<%-- File 객체 다루기 --%>
<%@ page import="java.io.File" %>
<%-- 이미지 파일 다루기 : 이미지 여부 확인, 이미지 사이즈 확인 --%>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
// form 검증(validation)
// '작성자' 입력 필수, '제목' 입력 필수
function chkSubmit(){
	var frm = document.forms["frm"];
	var name = frm["name"].value.trim();
	var subject = frm["subject"].value.trim();
	if(name == ""){
		alert("작성자 란은 반드시 입력해야 합니다");
		frm["name"].focus();
		return false;
	}
	if(subject == ""){
		alert("제목은 반드시 작성해야 합니다");
		frm["subject"].focus();
		return false;
	}
	return true;
}
</script>
<body>
<h2>글작성</h2>
<%-- 글 내용이 많을수 있기 때문에 POST 방식 사용 --%>
<form name="frm" action="writeOk.do" method="post" onsubmit="return chkSubmit()" enctype="Multipart/form-data">
<%--<form action="FileUpload.jsp" method="post" 
	enctype="Multipart/form-data">--%>
작성자:<input type="text" name="name"/><br>
제목:<input type="text" name="subject"/><br>
내용:<br>
<textarea name="content"></textarea>
<hr>
파일1: <input type="file" name="file1"><br> <%-- part: file1 --%> 
<%-- 파일2: <input type="file" name="file2"><br> <%-- part: file2 --%>
<br><br>
<input type="submit" value="등록"/>
</form>
<br>
<button type="button" onclick="location.href='list.do'">목록으로</button>
</body>
</html>