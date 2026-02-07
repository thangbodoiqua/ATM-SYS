<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
String ctx = request.getContextPath();
String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Enter ATM Card</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/input.css">

</head>
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/home.do" styleClass="back-link">← Back to Home</html:link>
				<h2 class="page-title">Enter card number</h2>
			</div>

			<html:form action="/atm/enter-card" method="POST">
				<div class="field">
					<div class="field-head">
						<label>Card Number</label>
						<%
						if (error != null) {
						%><span class="inline-errors"><%=error%></span>
						<%
						}
						%>
					</div>
					<html:text property="cardNumber" maxlength="16" />
				</div>

				<div class="actions">
					<span class="spacer"></span>
					<button type="submit" class="btn">Continue</button>
				</div>
			</html:form>

		</div>
	</div>


</body>
</html>