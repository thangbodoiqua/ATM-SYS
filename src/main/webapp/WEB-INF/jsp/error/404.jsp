<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 - Page Not Found</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/option.css">

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />
	<div class="page-container">
		<h2>404 - Page Not Found</h2>
		<p>The requested page does not exist.</p>
	</div>


</body>
</html>