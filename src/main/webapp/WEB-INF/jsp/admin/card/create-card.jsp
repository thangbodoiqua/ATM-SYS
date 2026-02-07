<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/input.css">

<title>Create Card</title>
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

	<div class="page-container">
		<div class="form-card">

			<div class="page-header">
				<html:link page="/admin.do" styleClass="back-link">← Back to dashboard</html:link>
				<h2 class="page-title">Create Card</h2>
			</div>

			<html:messages id="msg" message="true">
				<p class="success-text">
					<bean:write name="msg" />
				</p>
			</html:messages>
			<div class="global-errors">
				<html:errors property="error" />
			</div>

			<html:form action="/admin/card/create" method="post">

				<div class="field">
					<div class="field-head">
						<label>Username</label> <span class="inline-errors"><html:errors
								property="user" /></span>
					</div>
					<html:text property="username" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>Card Number (16 digits):</label> <span
							class="inline-errors"><html:errors property="cardNumber" /></span>
					</div>
					<html:text property="cardNumber" maxlength="16" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>PIN (4 digits):</label> <span class="inline-errors"><html:errors
								property="pinCode" /></span>
					</div>
					<html:text property="pinCode" maxlength="4" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>Card Type:</label> <span class="inline-errors"><html:errors
								property="cardType" /></span>
					</div>
					<html:select property="cardType">
						<html:option value="">-- Select --</html:option>
						<html:option value="DEBIT">DEBIT</html:option>
						<html:option value="CREDIT">CREDIT</html:option>
					</html:select>
				</div>

				<div class="field">
					<div class="field-head">
						<label>Expired Date:</label> <span class="inline-errors"><html:errors
								property="expiredDate" /></span>
					</div>
					<input type="date" name="expiredDate" />
				</div>

				<div class="field">
					<div class="field-head">
						<label>Balance:</label> <span class="inline-errors"><html:errors
								property="balance" /></span>
					</div>
					<html:text property="balance" />
				</div>

				<div class="actions">
					<span class="spacer"></span>
					<button type="submit" class="btn">Create</button>
				</div>

			</html:form>

		</div>
	</div>
</body>
</html>