<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctx = request.getContextPath();
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <title>Enter ATM Card</title>
    <style>
        body { font-family: system-ui, -apple-system, Segoe UI, Roboto, Helvetica, Arial; margin: 24px; }
        .wrap { max-width: 480px; margin: 0 auto; }
        .row { margin-bottom: 12px; }
        input[type=text] {
            width: 100%; padding: 10px 12px; border-radius: 8px;
            border: 1px solid #cbd5e1; font-size: 14px;
        }
        .btn { padding: 10px 14px; border-radius: 8px; border: none; background: #0ea5e9; color: #fff; cursor: pointer; }
        .link { display: inline-block; padding: 10px 14px; border-radius: 8px; background: #f1f5f9; text-decoration: none; color: #0f172a; }
        .error { padding: 10px 12px; background: #fee2e2; border: 1px solid #fecaca; color: #991b1b; border-radius: 8px; margin-bottom: 12px; }
        .actions { display: flex; gap: 10px; align-items: center; }
    </style>
</head>
<body>
<div class="wrap">
    <h1>Enter ATM Card</h1>

    <% if (error != null) { %>
      <div class="error"><%= error %></div>
    <% } %>

    <!-- Back về HOME -->
    <p class="row">
        <a class="link" href="<%=ctx%>/home.do">← Back to Home</a>
    </p>

    <!-- Form nhập số thẻ -->
    <form action="<%=ctx%>/atm/enter-card.do" method="post" autocomplete="off">
        <div class="row">
            <label>Card Number</label>
            <input type="text" name="cardNumber" required maxlength="19"
                   inputmode="numeric" pattern="[0-9 ]+"
                   placeholder="Ví dụ: 4111 1111 1111 1111">
        </div>
        <div class="actions">
            <button type="submit" class="btn">Tiếp tục</button>
        </div>
    </form>
</div>
</body>
</html>