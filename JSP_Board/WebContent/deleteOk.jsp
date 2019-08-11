<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	// dao 사용한 트랜잭션 생성
	int cnt = (Integer)request.getAttribute("result");
%>

<%-- 위에서 필요한 트랜잭션이 마무리 되면 페이지에 보여주기 --%>
<% if(cnt == 0){ %>
	<script>
		alert("삭제실패 !!!!");
		history.back();    // 브라우저가 직전에 기억하는 페이지로
	</script>
<% } else { %>
	<script>
		alert("삭제 성공, 리스트 출력합니다");
		location.href = "list.do";
	</script>
<% } %>
