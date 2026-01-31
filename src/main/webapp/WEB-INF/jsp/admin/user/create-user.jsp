<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
    String ctx = request.getContextPath();

    String message = (String) request.getAttribute("message");
    String error   = (String) request.getAttribute("error");

    String errUsername = (String) request.getAttribute("err_username");
    String errPassword = (String) request.getAttribute("err_password");
    String errEmail    = (String) request.getAttribute("err_email");
    String errPhone    = (String) request.getAttribute("err_phone");
    String errAddress  = (String) request.getAttribute("err_address");
    String errGender   = (String) request.getAttribute("err_gender");
    String errDob      = (String) request.getAttribute("err_dob");

    String dobValue = request.getParameter("dob") != null ?
                      request.getParameter("dob") : "";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create User</title>
</head>
<body>

<h1>Create User</h1>

<html:form action="/admin/user/create" method="POST">

  <% if (message != null) { %>
    <p style="color:green;"><%= message %></p>
  <% } %>

  <% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
  <% } %>


  <p>Username:</p>
  <html:text property="username" />
  <% if (errUsername != null) { %>
    <p style="color:red;"><%= errUsername %></p>
  <% } %>


  <p>Password:</p>
  <html:password property="password" />
  <% if (errPassword != null) { %>
    <p style="color:red;"><%= errPassword %></p>
  <% } %>


  <p>Email:</p>
  <html:text property="email" />
  <% if (errEmail != null) { %>
    <p style="color:red;"><%= errEmail %></p>
  <% } %>


  <!-- Phone -->
  <p>Phone:</p>
  <html:text property="phone" />
  <% if (errPhone != null) { %>
    <p style="color:red;"><%= errPhone %></p>
  <% } %>


  <!-- Address -->
  <p>Address:</p>
  <html:text property="address" />
  <% if (errAddress != null) { %>
    <p style="color:red;"><%= errAddress %></p>
  <% } %>


  <!-- Gender -->
  <p>Gender:</p>
  <label><html:radio property="gender" value="MALE" /> Male</label>
  <label><html:radio property="gender" value="FEMALE" /> Female</label>

  <% if (errGender != null) { %>
    <p style="color:red;"><%= errGender %></p>
  <% } %>


  <!-- Date of Birth -->
  <p>Date of Birth:</p>
  <input type="date" name="dob" value="<%= dobValue %>">

  <% if (errDob != null) { %>
    <p style="color:red;"><%= errDob %></p>
  <% } %>


  <p>
    <input type="submit" value="Create">
  </p>

</html:form>

</body>
</html>