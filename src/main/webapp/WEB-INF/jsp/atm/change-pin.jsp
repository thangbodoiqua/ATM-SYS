<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Pin</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/input.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/atm/menu.do" styleClass="back-link">← Back to menu</html:link>
				<h2 class="page-title">Change PIN</h2>
			</div>

			<html:messages id="msg" message="true">
				<p class="success-text">
					<bean:write name="msg" />
				</p>
			</html:messages>
			<div class="global-errors">
				<html:errors property="error" />
			</div>

			<html:form action="/atm/change-pin.do" method="POST">

				<div class="field">
					<div class="field-head">
						<label>Old PIN:</label> <span class="inline-errors"><html:errors
								property="oldPin" /></span>
					</div>
					<html:password property="oldPin" maxlength="4" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>New PIN:</label> <span class="inline-errors"><html:errors
								property="newPin" /></span>
					</div>
					<html:password property="newPin" maxlength="4" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>Confirm PIN:</label> <span class="inline-errors"><html:errors
								property="confirmPin" /></span>
					</div>
					<html:password property="confirmPin" maxlength="4" />
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