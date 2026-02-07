<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
String ctx = request.getContextPath();
String username = (String) session.getAttribute("AUTH_USERNAME");
if (username == null || username.trim().isEmpty()) username = "User";
%>

<link rel="stylesheet" href="<%=ctx%>/static/css/header.css">

<div class="site-header">
  <div class="site-header-inner">
    <div class="header-left">
<a href="${pageContext.request.contextPath}/home.do" class="brand-link">Home</a>    </div>

    <div class="header-right">
      <span class="username">Welcome, <%= username %></span>
      <html:link page="/auth/logout.do" styleClass="btn btn-sm btn-ghost">Logout</html:link>
    </div>
  </div>
</div>