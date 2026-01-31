<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
	String error = (String) request.getAttribute("error");
	String message = (String) request.getAttribute("message");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

<html:form action="/auth/login" method="POST">

    <% if (error != null) { %>
        <p style="color:red;"><%= error%></p>
    <% } %>

	<% if (message != null) { %>
        <p style="color:red;"><%= message%></p>
    <% } %>
    <p>Username:</p>
    <html:text property="username" />

    <p>Password:</p>
    <html:password property="password" />

    <p>
        <input type="submit" value="Login" />
    </p>

</html:form>

</body>
</html>