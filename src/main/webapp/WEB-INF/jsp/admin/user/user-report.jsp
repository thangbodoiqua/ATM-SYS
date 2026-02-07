<%@page import="java.text.SimpleDateFormat"%>
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
<title>User List</title>
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
				<h2 class="page-title">User List</h2>
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
							<th>User ID</th>
							<th>Username</th>
							<th>Email</th>
							<th>Phone</th>
							<th>Address</th>
							<th>Gender</th>
							<th>DOB</th>
							<th>Role</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>

						<logic:empty name="userReportForm" property="users">
							<tr>
								<td colspan="10" class="table-empty">No users</td>
							</tr>
						</logic:empty>

						<logic:notEmpty name="userReportForm" property="users">
							<logic:iterate name="userReportForm" property="users" id="u">
								<tr>
									<td><%=counter++%></td>
									<td><bean:write name="u" property="userId" /></td>
									<td><bean:write name="u" property="username" /></td>
									<td><bean:write name="u" property="email" /></td>
									<td><bean:write name="u" property="phone" /></td>
									<td><bean:write name="u" property="address" /></td>
									<td><bean:write name="u" property="gender" /></td>
									<td class="time"><bean:write name="u" property="dob"
											format="yyyy-MM-dd" /></td>
									<td><bean:write name="u" property="role" /></td>
									<td style="white-space: nowrap"><html:link
											action="/admin/user/update" paramId="username" paramName="u"
											paramProperty="username">Update</html:link> &nbsp;|&nbsp; <html:link
											action="/admin/user/reset-password" paramId="username"
											paramName="u" paramProperty="username">Reset Password</html:link>
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