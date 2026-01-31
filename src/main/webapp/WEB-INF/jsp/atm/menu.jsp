<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String ctx = request.getContextPath();
    	
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ATM Menu</title>
</head>
<body>
    <h2>ATM Menu</h2>
	<h3></h3>
    <p><a href = "<%=ctx%>/atm/transfer.do">Transfer</a></p>
    <p><a href = "<%=ctx%>/atm/withdraw.do">Withdraw</a></p>
    <p><a href = "<%=ctx%>/atm/enquiry.do">Enquiry</a></p>
    <p><a href = "<%=ctx%>/atm/deposit.do">Deposit</a></p>
    <p><a href = "<%=ctx%>/atm/change-in.do">Change Pin</a></p>
</body>
</html>