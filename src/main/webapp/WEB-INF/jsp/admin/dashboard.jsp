<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <title>Admin Dashboard</title>
</head>
<body>
<h2>Đăng nhập thành công!</h2>

<p>
    Xin chào,
    <bean:write name="AUTH_USER" property="username" scope="session"/>
</p>

<p>Đây là dashboard Admin (demo).</p>
</body>
</html>
``