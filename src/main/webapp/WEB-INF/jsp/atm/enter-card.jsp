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
    <meta charset="UTF-8"/>
    <title>Enter ATM Card</title>
</head>
<body>
    <h1>Enter ATM Card</h1>

    <% if (error != null) { %>
      <p style="color:red"><%= error %></p>
    <% } %>
    <p>
        <a href="<%=ctx%>/home.do">← Back to Home</a>
    </p>


	<html:form action="/atm/enter-card" method="POST">
	  <div>
	    <label>Card Number</label>
	    <html:text property="cardNumber" maxlength="16"/>
	  </div>
	  <div>
	    <html:submit>Continue</html:submit>
	  </div>
	</html:form>

</body>
</html>