<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctx = request.getContextPath();

    // Trạng thái phiên (hỗ trợ cả cách bạn đã dùng trước đó)
    Object principal = session.getAttribute("PRINCIPAL"); // đặt khi login thành công (đề xuất)
    Object userObj   = session.getAttribute("USER");      // nếu bạn đang dùng "USER"
    Object adminObj  = session.getAttribute("ADMIN_USER");// nếu bạn đang dùng "ADMIN_USER"
    boolean isAdminFlag = true;
    /* Boolean.TRUE.equals(session.getAttribute("IS_ADMIN")); */

    boolean isLoggedIn = true;
    /* (principal != null) || (userObj != null) || (adminObj != null); */
    boolean isAdmin    = true;
    /* isAdminFlag || (adminObj != null); */

    String loginError = (String) request.getAttribute("error"); // nếu LoginAction set lỗi
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <title>ATM System – Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <style>
        :root { --bg:#0f172a; --panel:#111827; --card:#1f2937; --muted:#9ca3af; --text:#e5e7eb; --hover:#374151;
                --accent:#22d3ee; --accent2:#a78bfa; --danger:#ef4444; }
        *{box-sizing:border-box}
        body{margin:0;background:radial-gradient(1200px 800px at 10% 10%,#0b1220,var(--bg));
             font-family:system-ui,-apple-system,Segoe UI,Roboto,Helvetica,Arial;color:var(--text)}
        header{padding:28px 20px 8px}
        header h1{margin:0;font-size:28px}
        .container{max-width:920px;margin:0 auto;padding:16px 20px 40px}
        .section{background:linear-gradient(180deg,rgba(255,255,255,.02),rgba(255,255,255,0)),var(--panel);
                 border:1px solid rgba(255,255,255,.06);border-radius:14px;padding:18px}
        .section h2{margin:0 0 10px;font-size:20px}
        .hint{color:var(--muted);font-size:13px;margin-bottom:14px}
        .field{display:grid;gap:6px;margin-bottom:12px}
        input[type=text],input[type=password]{width:100%;padding:10px 12px;border-radius:10px;
                 border:1px solid rgba(255,255,255,.08);background:var(--card);color:var(--text)}
        .row{display:flex;gap:12px;flex-wrap:wrap;align-items:center}
        .btn{display:inline-block;background:linear-gradient(90deg,var(--accent),var(--accent2));color:#031b1f;
             font-weight:700;border:none;border-radius:10px;padding:10px 14px;cursor:pointer;text-decoration:none}

        /* Lưới 2 option sau khi đăng nhập */
        .options{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:20px;margin-top:10px}
        @media(max-width:900px){.options{grid-template-columns:1fr}}
        .card-link{
            display:block;text-decoration:none;color:var(--text);
            background:var(--card);border:1px solid rgba(255,255,255,.06);
            border-radius:12px;padding:22px;text-align:center;transition:120ms ease;
        }
        .card-link:hover{background:var(--hover);border-color:rgba(255,255,255,.12)}
        .card-link h3{margin:0 0 6px;font-size:18px}
        .msg{padding:10px 12px;border-radius:10px;margin-bottom:12px;font-size:14px}
        .msg-error{background:rgba(239,68,68,.1);border:1px solid rgba(239,68,68,.35);color:#fecaca}
    </style>
</head>
<body>
<header class="container">
    <h1>Home</h1>
</header>

<main class="container">

    <% if (!isLoggedIn) { %>
        <!-- CHƯA đăng nhập: chỉ có form đăng nhập -->
        <section class="section" aria-labelledby="login-area">
            <h2 id="login-area">Login</h2>


            <% if (loginError != null) { %>
              <div class="msg msg-error"><%= loginError %></div>
            <% } %>

            <form method="post" action="<%=ctx%>/login.do" autocomplete="off">
                <div class="field">
                    <label>Username</label>
                    <input type="text" name="username" required maxlength="64">
                </div>
                <div class="field">
                    <label>Password</label>
                    <input type="password" name="password" required maxlength="128" autocomplete="current-password">
                </div>
                <div class="row">
                    <button type="submit" class="btn">Login</button>
                </div>
            </form>
        </section>
    <% } else { %>
        <!-- ĐÃ đăng nhập: chỉ hiển thị 2 option -->
        <section class="section" aria-labelledby="options-area">
            
            <div class="options">
                <!-- Option 1: Enter ATM Card -->
                <a class="card-link" href="<%=ctx%>/auth/enter-card.do">
                    <h3>Enter ATM Card</h3>
                </a>

                <!-- Option 2: Admin Dashboard (chỉ hiện nếu là admin) -->
                <% if (isAdmin) { %>
                <a class="card-link" href="<%=ctx%>/admin/dashboard.do"><!-- đổi link nếu bạn có /admin/dashboard.do -->
                    <h3>Admin Dashboard</h3>
                </a>
                <% } %>
            </div>

            <div class="row" style="margin-top:16px">
                <a class="btn" style="background:#1f2937;color:#e5e7eb;border:1px solid rgba(255,255,255,.12)"
                   href="<%=ctx%>/auth/logout.do">LOG OUT</a>               
            </div>
        </section>
    <% } %>

</main>
</body>
</html>