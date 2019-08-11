<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<%-- cos 라이브러리 --%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="com.oreilly.servlet.multipart.FileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%-- parameter 값들, file 값들 추출 --%>
<%@ page import="java.util.Enumeration"%>
<%-- File 객체 다루기 --%>
<%@ page import="java.io.File"%>
<%-- 이미지 파일 다루기 : 이미지 여부 확인, 이미지 사이즈 확인 --%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="javax.imageio.ImageIO"%>
<jsp:useBean id="dto" class="com.lec.beans.WriteDTO" />
<%
	int cnt = (Integer) request.getAttribute("result");
%>



<%-- 위에서 필요한 트랜잭션이 마무리 되면 페이지에 보여주기 --%>
<%
	if (cnt == 0) {
%>
<script>
	alert("등록 실패!!!");
	history.back(); // 브라우저가 직전에 기억하고 있던 페이지로 이동
</script>
<%
	} else {
%>
<script>
	alert("등록 성공, 리스트 출력합니다");
	location.href = "list.do";
</script>
<%
	}
%>
