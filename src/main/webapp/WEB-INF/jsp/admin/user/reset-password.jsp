<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reset Password</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/input.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/admin/user.do" styleClass="back-link">← Back to user report</html:link>
				<h2 class="page-title">Reset Password</h2>
			</div>

			<html:messages id="msg" message="true">
				<p class="success-text">
					<bean:write name="msg" />
				</p>
			</html:messages>
			<div class="global-errors">
				<html:errors property="error" />
			</div>
			<html:form action="/admin/user/reset-password" method="post">


				<div class="field">
					<div class="field-head">
						<label>Username:</label> <span class="inline-errors"><html:errors
								property="username" /></span>
					</div>					
					<html:text  property="username" maxlength="50" readonly="true"/>
				</div>

				<div class="field">
					<div class="field-head">
						<label>Old password:</label> <span class="inline-errors"><html:errors
								property="oldPassword" /></span>
					</div>
					<html:password property="oldPassword" maxlength="100" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>New password:</label> <span class="inline-errors"><html:errors
								property="newPassword" /></span>
					</div>
					<html:password property="newPassword" maxlength="100" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>Confirm password:</label> <span class="inline-errors"><html:errors
								property="confirmPassword" /></span>
					</div>
					<html:password property="confirmPassword" maxlength="100" />
				</div>

				<div class="actions">
					<span class="spacer"></span>
					<button type="submit" class="btn">Confirm</button>
				</div>

			</html:form>

		</div>
	</div>

</body>
</html>