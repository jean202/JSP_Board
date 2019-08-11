<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lec.beans.*"%>
<%@ page import="java.net.URLEncoder" %>
<%	request.setCharacterEncoding("utf-8");%>
<%
	String [] originalFileNames = request.getParameterValues("originalFileName");
	String [] fileSystemNames = request.getParameterValues("fileSystemName");
	int cnt = originalFileNames.length;	// 업로드된 파일 개수
%>
<%	// Controller로부터 결과 데이터 받음
	WriteDTO[] arr = (WriteDTO [])request.getAttribute("list");
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
	int write_id = arr[0].getId();
	String name = arr[0].getName();
	String subject = arr[0].getSubject();
	String content = arr[0].getContent();
	String regdate = arr[0].getRegDate();
	int viewcnt = arr[0].getViewCnt();
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>읽기 <%=subject%></title>
</head>
<script>
function chkDelete(write_id){
	// 삭제여부, 확인하고 진행하기
	var r = confirm("삭제하시겠습니까?");
	
	if(r){
		location.href = "deleteOk.do?write_id=" + write_id;
	}	
}
</script>
<body>
	<h2>
		읽기
		<%=subject%></h2>
	<br> id :<%=write_id%>
	<br> 작성자 :<%=name%>
	<br> 제목 : <%=subject%>
	<br> 등록일 :<%=regdate%>
	<br> 조회수 :<%=viewcnt%>
	<br> 내용 :
	<br>
	<hr>
	<div>
		<%=content%>
	</div>	
<h3>이미지 보기</h3>
<% for(int i = 0; i < originalFileNames.length; i++){ %>
<div style="width:300px">
	<img style="width:100%; height:auto;" src="upload/<%=fileSystemNames[i]%>">
<% } %>
<hr>
<h3>다운로드</h3>
<%
	for(int i = 0; i < cnt; i++){ %>
	<%-- 화면에는 사용자가 올린 원본 파일의 이름으로 나오고, 실제 다운로드 링크는 저장된 파일 이름으로 설정되어야 한다 --%>
	<a href="FileDownload.jsp?fileSystemName=<%=URLEncoder.encode(fileSystemNames[i], "utf-8")%>&originalFileName=<%=URLEncoder.encode(originalFileNames[i], "utf-8")%>"><%= originalFileNames[i] %></a>
	<br>
<%	} %>
<hr>
	<br>
	<button onclick="location.href='update.do?id=<%=write_id%>'">수정하기</button>
	<button onclick="location.href='list.do'">목록보기</button>
	<button onclick="location.href='write.do'">새글등록</button>
	<button onclick="chkDelete(<%=write_id%>)">삭제하기</button>
</div>
</body>
</html>