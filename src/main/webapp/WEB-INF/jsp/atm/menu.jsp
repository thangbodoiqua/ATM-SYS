<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();
BigDecimal balance = (BigDecimal) request.getAttribute("balance");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>ATM Menu</title>
  <link rel="stylesheet" href="<%=ctx%>/static/css/option.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

  <div class="page-container">
    <div class="form-card">
      <div class="page-header">
        <h2 class="page-title">ATM Menu</h2>
      </div>

      <% if (balance != null) { %>
        <p style="color: blue; text-align:center;">Current balance: <%= balance %></p>
      <% } %>

      <div class="menu-list">
        <html:link page="/atm/transfer.do" styleClass="btn btn-block">Transfer</html:link>
        <html:link page="/atm/deposit.do" styleClass="btn btn-block">Deposit</html:link>
        <html:link page="/atm/withdraw.do" styleClass="btn btn-block">Withdraw</html:link>
        <html:link page="/atm/enquiry.do" styleClass="btn btn-block">Enquiry balance</html:link>
        <html:link page="/atm/change-pin.do" styleClass="btn btn-block">Change pin</html:link>
        <html:link page="/atm/card/history.do" styleClass="btn btn-block">Transaction history</html:link>
      </div>

    </div>
  </div>

</body>
</html>
s