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
<%@ page import="java.sql.Connection,java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%
    String pp_asset_tag = request.getParameter("pp_asset_tag");
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/DriveTracking", "root", "@Rm@d1ll0!");
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("Select attachment_id, attachment_path, pp_asset_tag from attachment_info");
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
