<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	// Controller로부터 결과 데이터 받아오기
	int cnt = (Integer)request.getAttribute("result");
	int id = Integer.parseInt(request.getParameter("id"));
%>
<%-- 위에서 필요한 트랜잭션이 마무리 되면 페이지에 보여주기 --%>
<% if(cnt == 0){ %>
	<script>
		alert("수정실패 !!!!");
		history.back();    // 브라우저가 직전에 기억하는 페이지로
	</script>
<% } else { %>
	<script>
		alert("수정 성공");
		location.href = "view.do?id=<%= id %>";
	</script>
<% } %>















