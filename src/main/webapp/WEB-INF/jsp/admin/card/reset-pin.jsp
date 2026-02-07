<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Reset PIN</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/input.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

  <div class="page-container">
    <div class="form-card">

      <div class="page-header">
        <html:link page="/admin/card.do" styleClass="back-link">← Back to card report</html:link>
        <h2 class="page-title">Reset PIN</h2>
      </div>

      <html:messages id="msg" message="true">
        <p class="success-text"><bean:write name="msg"/></p>
      </html:messages>
      <div class="global-errors"><html:errors property="error"/></div>

      <html:form action="/admin/card/reset-pin" method="post">

        <div class="field">
          <div class="field-head">
            <label>Card Number:</label>
            <span class="inline-errors"><html:errors property="cardNumber"/></span>
          </div>
          <html:text property="cardNumber" maxlength="16"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Old PIN (4 digits):</label>
            <span class="inline-errors"><html:errors property="oldPin"/></span>
          </div>
          <html:password property="oldPin" maxlength="4"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>New PIN (4 digits):</label>
            <span class="inline-errors"><html:errors property="newPin"/></span>
          </div>
          <html:password property="newPin" maxlength="4"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Confirm PIN (4 digits):</label>
            <span class="inline-errors"><html:errors property="confirmPin"/></span>
          </div>
          <html:password property="confirmPin" maxlength="4"/>
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