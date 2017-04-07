<%--<%@ page language="java" contentType="text/html; charset=UTF-8"--%>
         <%--pageEncoding="UTF-8"%>--%>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">--%>

<%--<html>--%>
<%--<head>--%>
    <%--<title>File Upload</title>--%>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>

    <%--<link href="css/bootstrap.css" rel="stylesheet">--%>
    <%--<!--  <link href="css/datepicker.css" rel="stylesheet" type="text/css"> -->--%>

    <%--<!-- jquery datepicker ui css, used here for datepicker -->--%>
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />--%>
    <%--<link href="css/font-awesome.css" rel="stylesheet">--%>
    <%--<link rel="stylesheet" href="css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="css/font-awesome.min.css">--%>
    <%--<!--[if IE 7]>--%>
    <%--<link rel="stylesheet" href="css/font-awesome-ie7.min.css">--%>
    <%--<![endif]-->--%>
    <%--<link href="css/bootstrap-responsive.css" rel="stylesheet">--%>
    <%--<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->--%>
    <%--<!--[if lt IE 9]>--%>
    <%--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>--%>
    <%--<![endif]-->--%>
    <%--<link href="css/bootstrap-responsive.min.css" rel="stylesheet">--%>
    <%--<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">--%>
    <%--&lt;%&ndash;<!-- Latest compiled and minified CSS -->&ndash;%&gt;--%>
    <%--<link href="css/bootstrap2.css" rel="stylesheet">--%>
    <%--<link href="css/bootstrap2.min.css" rel="stylesheet">--%>
    <%--&lt;%&ndash;<!-- Optional theme -->&ndash;%&gt;--%>
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">--%>

    <%--<!-- jquery datepicker ui css, used here for datepicker -->--%>
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />--%>
    <%--<link href="css/style2.css" rel="stylesheet">--%>

    <%--<style>--%>
        <%--body{--%>
            <%--margin-left: 1%;--%>
        <%--}--%>
        <%--#tableCounter{--%>
            <%--text-align: center;--%>
            <%--padding: 3%;--%>
        <%--}--%>
        <%--#description{--%>
            <%--padding: 3%;--%>
        <%--}--%>
    <%--</style>--%>

<%--</head>--%>
<%--<body>--%>
<%--<div id="uploadFile" style="margin-top: 1%;">--%>
    <%--<h4>File Upload</h4>--%>
    <%--<form method="POST" action="FileUploadDBServlet" enctype="multipart/form-data">--%>
        <%--<table>--%>
            <%--<tr><td>Asset Tag PS</td>--%>
                <%--<td><input type="text" name="assetTag" id="assetTag" readonly/></td>--%>
            <%--<tr><td>Description</td>--%>
                <%--<td><input type="text" name="description" /></td>--%>
            <%--<tr>--%>
            <%--<tr><td>PDF File</td>--%>
                <%--<td><input type="file" name="file" id="file" /> </td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="2">--%>
                    <%--&lt;%&ndash;<input type="submit" value="Upload" name="upload" id="upload" /> </td>&ndash;%&gt;--%>
                    <%--<button type="submit" class="btn btn-primary" id="upload">Upload</button>--%>

            <%--</tr>--%>
        <%--</table>--%>
    <%--</form>--%>
<%--</div>--%>

<%--<%@ page import="java.sql.*,db_credentials.mysql_credentials"%>--%>
<%--<%--%>
    <%--String assetTag = "'" + request.getParameter("pp_asset_tag") + "'";--%>
    <%--System.out.println(assetTag);--%>
    <%--String query = "Select pp_asset_tag, file, description from upload where pp_asset_tag = " + assetTag;--%>

    <%--String BookId = request.getParameter("pp_asset_tag");--%>
    <%--Class.forName("com.mysql.jdbc.Driver").newInstance();--%>
    <%--Connection con = DriverManager.getConnection(mysql_credentials.db_url, mysql_credentials.user_name, mysql_credentials.password);--%>
    <%--Statement st = con.createStatement();--%>
    <%--ResultSet rs = st.executeQuery(query);--%>
<%--%>--%>

<%--<h4 >Uploaded Files</h4>--%>
<%--<table cellpadding="15" border="1">--%>
    <%--<tr>--%>
        <%--<th>No. </th>--%>
        <%--<th style="text-align: center">Description</th>--%>
    <%--</tr>--%>
    <%--<%--%>
        <%--int i = 1;--%>
        <%--while(rs.next()){--%>
    <%--%>--%>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<div id="tableCounter"><%=i%></div>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--&lt;%&ndash;<div>Asset Tag: <%=rs.getString("pp_asset_tag")%></div>&ndash;%&gt;--%>
            <%--<div id="description"><a href="<%="FileReadPdf?assetTag=" + rs.getString("pp_asset_tag") + "&" + "description=" + rs.getString("description")%>" target="_blank"><%=rs.getString("description")%></a></div>--%>
        <%--</td>--%>

    <%--</tr>--%>
    <%--<%--%>
            <%--i++;--%>
        <%--}%>--%>
<%--</table>--%>
<%--</body>--%>

<%--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>--%>
<%--<script type="text/javascript" src="js/upload_script.js"></script>--%>
<%--</html>--%>