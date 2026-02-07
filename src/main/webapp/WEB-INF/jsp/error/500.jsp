<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 - Server Error</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/option.css">

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />
	<div class="page-container">

	<h2>500 - Internal Server Error</h2>
    <p>Something went wrong on the server.</p>
</div>
</body>
</html>