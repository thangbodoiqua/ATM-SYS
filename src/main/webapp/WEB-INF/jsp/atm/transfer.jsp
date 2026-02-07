<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfer</title>

  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/input.css">

</head>
<body>

		<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />
	
<div class="page-container">
    <div class="form-card">

      <div class="page-header">
        <html:link page="/atm/menu.do" styleClass="back-link">← Back to menu</html:link>
        <h2 class="page-title">Transfer</h2>
      </div>

      <html:messages id="msg" message="true">
        <p class="success-text"><bean:write name="msg"/></p>
      </html:messages>
      <div class="global-errors">
        <html:errors property="error"/>
      </div>

      <html:form action="/atm/transfer" method="POST">
        <div class="field">
          <div class="field-head">
            <label>Receiver card number</label>
            <span class="inline-errors"><html:errors property="receiverCardNumber"/></span>
          </div>
          <html:text property="receiverCardNumber" maxlength="16"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Amount</label>
            <span class="inline-errors"><html:errors property="amount"/></span>
          </div>
          <html:text property="amount"/>
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