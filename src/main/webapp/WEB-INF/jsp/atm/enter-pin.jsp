<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<%
String ctx = request.getContextPath();
String error = (String) request.getAttribute("error");
String cardNumber = (String) session.getAttribute("ATM_CARD_NUMBER");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Enter ATM Pin</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/input.css">

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/atm/enter-card.do" styleClass="back-link">← Back to enter card</html:link>
				<h2 class="page-title">Enter ATM Pin</h2>
			</div>
			<html:messages id="msg" message="true">
				<p class="success-text">
					<bean:write name="msg" />
				</p>
			</html:messages>
			<html:form action="/atm/enter-pin" method="POST">
				<div class="field">
					<div class="field-head">
						<label>Card Number</label>
					</div>
					<input type="text" value="<%=cardNumber%>" readonly>
				</div>

				<div class="field">
					<div class="field-head">
						<label>Pin</label>
						<%
						if (error != null) {
						%><span class="inline-errors"><%=error%></span>
						<%
						}
						%>
					</div>
					<html:password property="pin" maxlength="4" />
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