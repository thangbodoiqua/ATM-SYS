<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
    String ctx = request.getContextPath();
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfer</title>
</head>
<body>

<h2>Transfer</h2>

	<% if (error != null) { %>
	    <p style="color:red;"><%= error %></p>
	<% } %>

<html:form action="/atm/transfer" method="POST">

    <p>
        Receiver Card Number:<br>
        <html:text property="receiverCardNumber" />
    </p>

    <p>
        Amount:<br>
        <html:text property="amount" />
    </p>

    <p>
        <html:submit value="Confirm" />
    </p>

</html:form>

</body>
</html>