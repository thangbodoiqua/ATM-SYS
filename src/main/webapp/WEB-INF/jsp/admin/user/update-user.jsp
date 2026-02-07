<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/input.css">
  <title>Update User</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

  <div class="page-container">
    <div class="form-card">

      <div class="page-header">
        <html:link page="/admin/user.do" styleClass="back-link">← Back to user report</html:link>
        <h2 class="page-title">Update User</h2>
      </div>

      <html:messages id="msg" message="true">
        <p class="success-text"><bean:write name="msg"/></p>
      </html:messages>
      <div class="global-errors"><html:errors property="error"/></div>

      <html:form action="/admin/user/update" method="POST">
        <html:hidden property="username"/>

        <div class="field">
          <div class="field-head" style="display: block">
            <label>Username:</label>
            <span style="color:blue"><bean:write name="updateUserForm" property="username"/></span>
          </div>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Email:</label>
            <span class="inline-errors"><html:errors property="email"/></span>
          </div>
          <html:text property="email" maxlength="100"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Phone:</label>
            <span class="inline-errors"><html:errors property="phone"/></span>
          </div>
          <html:text property="phone" maxlength="20"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Address:</label>
            <span class="inline-errors"><html:errors property="address"/></span>
          </div>
          <html:text property="address" maxlength="200"/>
        </div>

        <div class="actions">
          <span class="spacer"></span>
          <button type="submit" class="btn">Save</button>
        </div>
      </html:form>

    </div>
  </div>

</body>
</html>