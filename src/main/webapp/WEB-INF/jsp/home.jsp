<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
String ctx = request.getContextPath();

String role = (String) session.getAttribute("AUTH_ROLE");
boolean isAdmin = "ADMIN".equals(role);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/option.css">

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />
	<div class="page-container">
		<div class="form-card">
			<div class="page-header">
				<h2 class="page-title">Home</h2>
			</div>

			<div class="menu-list">
				<html:link page="/atm/enter-card.do" styleClass="btn btn-block">Enter ATM Card</html:link>

				<%
				if (isAdmin) {
				%>
				<html:link page="/admin.do" styleClass="btn btn-block">Admin Dashboard</html:link>
				<%
				}
				%>

				<html:link page="/user/history.do" styleClass="btn btn-block">Transaction history</html:link>
			</div>

		</div>
	</div>


</body>
</html>
