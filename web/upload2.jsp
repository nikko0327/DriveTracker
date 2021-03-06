<%--
  Created by IntelliJ IDEA.
  User: zgraham
  Date: 3/31/17
  Time: 1:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String assetTag = request.getParameter("pp_asset_tag");
        boolean update = Boolean.valueOf(request.getParameter("update"));//parses update value(true or false in string) to a boolean

        System.out.println("asset: " + assetTag);
        System.out.println("update: " + update);
        //if it is not called for an update, then dont display the form (since the user isnt updating)
        String hide = "";
        if (!update) {
            hide = "hidden";
        }

        con = db_credentials.DB.getConnection();
        ps = con.prepareStatement("SELECT * FROM upload WHERE pp_asset_tag = ?");
        ps.setString(1, assetTag);
        rs = ps.executeQuery();
%>
<div id="uploadFile" style="margin-top: 1%;">
    <form method="POST" action="FileUploadDBServlet" enctype="multipart/form-data" <%=hide%>>
        <!--have to use style display:none because using type="hidden" breaks sending in the form to FileUploadDBServlet (assetTag will have no value) -->
        <input type="text" name="assetTag" id="assetTag" style="display:none"/>
        <table>
            <tr>
                <td>Description</td>
                <td><input type="text" name="description" id="desc"/></td>
            </tr>
            <tr>
                <td>PDF File</td>
                <td><input type="file" name="file" id="file"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <%--<input type="submit" value="Upload" name="upload" id="upload" /> </td>--%>
                    <button type="submit" class="btn btn-primary" id="upload">Upload</button>
            </tr>
        </table>
    </form>
</div>

<h4>Uploaded Files</h4>
<table cellpadding="15" border="1">
    <tr>
        <th>ID</th>
        <th style="text-align: center">Description</th>
        <% if (update) { %>
        <th id="deleteHeader" style="text-align: center">dlt</th>
        <% } %>
    </tr>
    <%
        while (rs.next()) {
            // System.out.println("[upload2.jsp] RS has next.");
    %>
    <tr>
        <td>
            <div id="fileID<%= rs.getString("id")%>"><%=rs.getString("id")%>
            </div>
        </td>
        <td>
            <div id="description"><a
                    href="<%="FileReadPdf?assetTag=" + rs.getString("pp_asset_tag") + "&" + "description=" + rs.getString("description")%>"
                    target="_blank"><%=rs.getString("description")%>
            </a></div>
        </td>
        <% if (update) { %>
        <td>
            <button name='deletePDFButton' class='btn btn-xs btn-danger' id='deletePDF_<%=rs.getString("id")%>'><i
                    class='icon-trash'></i></button>
        </td>
        <% } %>
    </tr>
    <%
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            db_credentials.DB.closeResources(con, ps, rs);
        }
    %>
</table>