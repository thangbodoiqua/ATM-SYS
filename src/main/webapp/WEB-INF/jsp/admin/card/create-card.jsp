<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String userIdVal     = (String) request.getAttribute("userId");
    String cardNumberVal = (String) request.getAttribute("cardNumber");
    String pinVal        = (String) request.getAttribute("pinCode");
    String cardTypeVal   = (String) request.getAttribute("cardType");
    String cardStatusVal = (String) request.getAttribute("cardStatus");
    String expiredVal    = (String) request.getAttribute("expiredDate");
    String balanceVal    = (String) request.getAttribute("balance");

    String message = (String) request.getAttribute("message");
    String error   = (String) request.getAttribute("error");

    String errUserId      = (String) request.getAttribute("err_userId");
    String errCardNumber  = (String) request.getAttribute("err_cardNumber");
    String errPin         = (String) request.getAttribute("err_pin");
    String errType        = (String) request.getAttribute("err_cardType");
    String errStatus      = (String) request.getAttribute("err_cardStatus");
    String errExpired     = (String) request.getAttribute("err_expiredDate");
    String errBalance     = (String) request.getAttribute("err_balance");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Card</title>
</head>
<body>

<h2>Create Card</h2>

<% if (message != null) { %>
    <p style="color: green;"><%= message %></p>
<% } %>

<% if (error != null) { %>
    <p style="color: red;"><%= error %></p>
<% } %>

<form action="create.do" method="post">

    <p>
        User ID:<br>
        <input type="text" name="userId" value="<%= userIdVal != null ? userIdVal : "" %>">
        <% if (errUserId != null) { %>
            <span style="color:red;"><%= errUserId %></span>
        <% } %>
    </p>

    <p>
        Card Number (16 digits):<br>
        <input type="text" name="cardNumber" maxlength="16"
               value="<%= cardNumberVal != null ? cardNumberVal : "" %>">
        <% if (errCardNumber != null) { %>
            <span style="color:red;"><%= errCardNumber %></span>
        <% } %>
    </p>

    <p>
        PIN (4 digits):<br>
        <input type="text" name="pinCode" maxlength="4"
               value="<%= pinVal != null ? pinVal : "" %>">
        <% if (errPin != null) { %>
            <span style="color:red;"><%= errPin %></span>
        <% } %>
    </p>

    <p>
        Card Type:<br>
        <select name="cardType">
            <option value="">-- Select --</option>
            <option value="DEBIT"  <%= "DEBIT".equalsIgnoreCase(cardTypeVal) ? "selected" : "" %>>DEBIT</option>
            <option value="CREDIT" <%= "CREDIT".equalsIgnoreCase(cardTypeVal) ? "selected" : "" %>>CREDIT</option>
        </select>
        <% if (errType != null) { %>
            <span style="color:red;"><%= errType %></span>
        <% } %>
    </p>

    <p>
        Card Status:<br>
        <select name="cardStatus">
            <option value="">-- Select --</option>
            <option value="ACTIVE"  <%= "ACTIVE".equalsIgnoreCase(cardStatusVal) ? "selected" : "" %>>ACTIVE</option>
            <option value="BLOCKED"  <%= "BLOCKED".equalsIgnoreCase(cardStatusVal) ? "selected" : "" %>>BLOCKED</option>
            <option value="EXPIRED" <%= "EXPIRED".equalsIgnoreCase(cardStatusVal) ? "selected" : "" %>>EXPIRED</option>
        </select>
        <% if (errStatus != null) { %>
            <span style="color:red;"><%= errStatus %></span>
        <% } %>
    </p>

    <p>
        Expired Date:<br>
        <input type="date" name="expiredDate" value="<%= expiredVal != null ? expiredVal : "" %>">
        <% if (errExpired != null) { %>
            <span style="color:red;"><%= errExpired %></span>
        <% } %>
    </p>

    
<p>
        Balance: <br>
        <input type="text" name="balance" value="<%= balanceVal != null ? balanceVal : "" %>">
        <% if (errBalance != null) { %>
            <span style="color:red;"><%= errBalance %></span>
        <% } %>
    </p>

    <p>
        <button type="submit">Create</button>
    </p>

</form>
