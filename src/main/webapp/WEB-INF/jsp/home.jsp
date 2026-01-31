<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctx = request.getContextPath();

    String username = (String) session.getAttribute("AUTH_USERNAME");
    String role     = (String) session.getAttribute("AUTH_ROLE");

    boolean isAdmin = "ADMIN".equals(role); // tránh NPE bằng cách gọi equals trên hằng
    if (username == null || username.trim().isEmpty()) {
        username = "User";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>

<h1>Welcome, <%= username %>!</h1>

<h2>Options</h2>

<ul>
    <!-- Always show Enter ATM Card -->
    <li>
        <a href="<%= ctx %>/atm/enter-card.do">Enter ATM Card</a>
    </li>

    <% if (isAdmin) { %>
        <li>
            <a href="<%= ctx %>/admin">Admin Dashboard</a>
        </li>
    <% } %>
    
    <li>
        <a href="<%= ctx %>/auth/logout.do">Logout</a>
    </li>
</ul>

</body>
</html>
