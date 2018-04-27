<%--
  Created by IntelliJ IDEA.
  User: nlee
  Date: 7/26/16
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username"))
                userName = cookie.getValue();
        }
    }

    if (userName == null)
        response.sendRedirect("/DriveTracker");
%>
<body>
<%@ page import="java.sql.Connection,java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    String pp_asset_tag = request.getParameter("pp_asset_tag");

    Connection con = db_credentials.DB.getConnection();
    PreparedStatement ps = con.prepareStatement("Select attachment_id, attachment_path, pp_asset_tag from attachment_info");
    ResultSet rs = ps.executeQuery();
%>
<table cellpadding="15" border="1">
    <%
        while (rs.next()) {
    %>
    <tr>
        <td>
            <div>Attachment ID: <%=rs.getString("attachment_id")%>
            </div>
            <div><a href="<%="/" + rs.getString("attachment_path")%>"><%=rs.getString("attachment_path")%>
            </a></div>
        </td>

    </tr>
    <%}%>
</table>

</body>
</html>
