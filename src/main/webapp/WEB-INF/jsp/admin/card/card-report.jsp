<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
int counter = 1;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Card List</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/table.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container"
		style="max-width: 1280px; margin margin: 10px auto 6px;">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/admin.do" styleClass="back-link">← Back to dashboard</html:link>
				<h2 class="page-title">Card List</h2>
			</div>

			<html:messages id="msg" message="true">
				<p class="success-text">
					<bean:write name="msg" />
				</p>
			</html:messages>
			<div class="global-errors">
				<html:errors property="error" />
			</div>

			<div class="table-wrap" style="margin-top: 12px">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>Card ID</th>
							<th>User ID</th>
							<th>Card Number</th>
							<th>Card Type</th>
							<th>Status</th>
							<th>Created</th>
							<th>Expired</th>
							<th>Balance</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>

						<logic:empty name="cardReportForm" property="cards">
							<tr>
								<td colspan="10" class="table-empty">No cards</td>
							</tr>
						</logic:empty>

						<logic:notEmpty name="cardReportForm" property="cards">
							<logic:iterate name="cardReportForm" property="cards" id="c">
								<tr>
									<td><%=counter++%></td>
									<td><bean:write name="c" property="cardId" /></td>
									<td><bean:write name="c" property="userId" /></td>
									<td><bean:write name="c" property="cardNumber" /></td>
									<td><bean:write name="c" property="cardType" /></td>
									<td><bean:write name="c" property="cardStatus" /></td>
									<td class="time"><bean:write name="c"
											property="createdDate" format="yyyy-MM-dd" /></td>
									<td class="time"><bean:write name="c"
											property="expiredDate" format="yyyy-MM-dd" /></td>
									<td><bean:write name="c" property="balance" /></td>
									<td style="white-space: nowrap"><html:link
											action="/admin/card/reset-pin" paramId="cardNumber"
											paramName="c" paramProperty="cardNumber">Reset PIN</html:link>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>

					</tbody>
				</table>
			</div>

		</div>
	</div>

</body>
</html>