<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
    String ctx = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String cardNumber = (String) session.getAttribute("ATM_CARD_NUMBER");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Enter ATM Pin</title>
</head>
<body>
    <h1>Enter ATM Pin</h1>

    <% if (error != null) { %>
      <p style="color:red"><%= error %></p>
    <% } %>
    <p>
        <a href="<%=ctx%>/atm/enter-card.do">← Back</a>
    </p>


	<html:form action="/atm/enter-pin" method="POST">
		<div>
	    <label>Card Number</label>
		<input type="text" value="<%= cardNumber %>" readonly>
	  </div>
	  <div>
	    <label>Pin</label>
	    <html:password property="pin" maxlength="4"/>
	  </div>
	  <div>
	    <html:submit>Continue</html:submit>
	  </div>
	</html:form>

</body>
</html>