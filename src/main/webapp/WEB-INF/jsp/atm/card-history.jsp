<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
int counter = 1;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction History</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/table.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container"
		style="max-width: 1280px; margin margin: 10px auto 6px;">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/atm/menu.do" styleClass="back-link">← Back to menu</html:link>
				<h2 class="page-title">Transaction History</h2>
			</div>

			<html:messages id="msg" message="true">
				<p class="success-text">
					<bean:write name="msg" />
				</p>
			</html:messages>
			<div class="global-errors">
				<html:errors property="error" />
			</div>

			<html:form action="/atm/card/history" method="POST">
				<div class="table-filters">
					<div class="field">
						<div class="field-head">
							<label>From</label> <span class="inline-errors"><html:errors
									property="from" /></span>
						</div>
						<input type="date" name="from">
					</div>

					<div class="field">
						<div class="field-head">
							<label>To</label> <span class="inline-errors"><html:errors
									property="to" /></span>
						</div>
						<input type="date" name="to">
					</div>

					<button type="submit" class="btn search-btn">SEARCH</button>
				</div>
			</html:form>

			<div class="table-wrap" style="margin-top: 12px">
				<table class="table">
					<thead>
						<tr>
							<th>Number</th>
							<th>Type</th>
							<th style="text-align: right">Amount</th>
							<th>Time</th>
							<th>Card Number</th>

						</tr>
					</thead>
					<tbody>
						<logic:empty name="cardHistoryForm" property="trans">
							<tr>
								<td colspan="4" class="table-empty">No transactions</td>
							</tr>
						</logic:empty>

						<logic:notEmpty name="cardHistoryForm" property="trans">
							<logic:iterate name="cardHistoryForm" property="trans" id="t">
								<tr>
									<td><%=counter++%></td>
									<td class="type"><bean:write name="t" property="transType" /></td>
									<td class="amount">
										  <logic:equal name="t" property="transType" value="withdraw">-<bean:write name="t" property="transAmount" /></logic:equal>
										  <logic:equal name="t" property="transType" value="WITHDRAW">-<bean:write name="t" property="transAmount" /></logic:equal>
										  <logic:equal name="t" property="transType" value="deposit">+<bean:write name="t" property="transAmount" /></logic:equal>
										  <logic:equal name="t" property="transType" value="DEPOSIT">+<bean:write name="t" property="transAmount" /></logic:equal>
										</td>
									<td class="time"><bean:write name="t" property="transTime"
											format="yyyy-MM-dd HH:mm:ss" /></td>
									<td><bean:write name="t" property="refCardNumber" /></td>

								</tr>
							</logic:iterate>
						</logic:notEmpty>
						<tr>
							<td colspan="5" class="table-more"
								style="text-align: center; color: #374151; font-size: 13px; padding: 8px 0;">
								Use the filters above to view more…</td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>

</body>
</html>