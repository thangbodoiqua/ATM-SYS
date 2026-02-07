<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<%
	String error = (String) request.getAttribute("error");
	String message = (String) request.getAttribute("message");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/input.css">
</head>
<body>


 
  <div class="page-container">
    <div class="form-card">

      <div class="page-header">
        <h2 class="page-title">Login</h2>
      </div>

      <html:messages id="msg" message="true">
        <p class="success-text"><bean:write name="msg"/></p>
      </html:messages>
      <div class="global-errors"><html:errors property="error"/></div>

      <html:form action="/auth/login" method="post">

        <div class="field">
          <div class="field-head">
            <label>Username:</label>
            <span class="inline-errors"><html:errors property="username"/></span>
          </div>
          <html:text property="username" maxlength="50"/>
        </div>

        <div class="field">
          <div class="field-head">
            <label>Password:</label>
            <span class="inline-errors"><html:errors property="password"/></span>
          </div>
          <html:password property="password" maxlength="100"/>
        </div>

        <div class="actions">
          <span class="spacer"></span>
          <button type="submit" class="btn">Login</button>
        </div>

      </html:form>

    </div>
  </div>


</body>
</html>