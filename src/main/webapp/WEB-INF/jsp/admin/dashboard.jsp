<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="<%=ctx%>/static/css/option.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" flush="true" />

  <div class="page-container">
    <div class="form-card">
      <div class="page-header">
        <h2 class="page-title">Admin Dashboard</h2>
      </div>

      <div class="menu-list">
        <html:link page="/admin/user.do" styleClass="btn btn-block">
          Users report
        </html:link>

        <html:link page="/admin/user/create.do" styleClass="btn btn-block">
          Create user
        </html:link>

        <html:link page="/admin/card.do" styleClass="btn btn-block">
          Cards report
        </html:link>

        <html:link page="/admin/card/create.do" styleClass="btn btn-block">
          Create card
        </html:link>

      </div>

    </div>
  </div>

</body>
</html>