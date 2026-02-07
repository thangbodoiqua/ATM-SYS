<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create User</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/input.css">
  
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

  <div class="page-container">
    <div class="form-card">

      <div class="page-header">
        <html:link page="/admin.do" styleClass="back-link">← Back to dashboard</html:link>
        <h2 class="page-title">Create User</h2>
      </div>

      <html:messages id="msg" message="true">
        <p class="success-text"><bean:write name="msg"/></p>
      </html:messages>
      <div class="global-errors">
        <html:errors property="error"/>
      </div>

      <html:form action="/admin/user/create" method="post">

        <div class="field">
          <div class="field-head">
            <label>Username:</label>
            <span class="inline-errors"><html:errors property="username"/></span>
          </div>
          <html:text property="username" maxlength="20"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Password:</label>
            <span class="inline-errors"><html:errors property="password"/></span>
          </div>
          <html:password property="password" maxlength="20"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Email:</label>
            <span class="inline-errors"><html:errors property="email"/></span>
          </div>
          <html:text property="email" maxlength="50"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Phone:</label>
            <span class="inline-errors"><html:errors property="phone"/></span>
          </div>
          <html:text property="phone" maxlength="16"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Address:</label>
            <span class="inline-errors"><html:errors property="address"/></span>
          </div>
          <html:text property="address"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Gender:</label>
            <span class="inline-errors"><html:errors property="gender"/></span>
          </div>
          <div class="radio-group">
            <label><html:radio property="gender" value="MALE"/> Male</label>
            <label><html:radio property="gender" value="FEMALE"/> Female</label>
          </div>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Date of Birth:</label>
            <span class="inline-errors"><html:errors property="dob"/></span>
          </div>
          <input type="date" name="dob" value="<bean:write name='createUserForm' property='dob'/>">
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