<%--
  Created by IntelliJ IDEA.
  User: nlee
  Date: 10/18/16
  Time: 12:54 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Retrieve File</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <link href="css/bootstrap.css" rel="stylesheet">
    <!--  <link href="css/datepicker.css" rel="stylesheet" type="text/css"> -->

    <!-- jquery datepicker ui css, used here for datepicker -->
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
    <link href="css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <!--[if IE 7]>
    <link rel="stylesheet" href="css/font-awesome-ie7.min.css">
    <![endif]-->
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">
    <%--<!-- Latest compiled and minified CSS -->--%>
    <link href="css/bootstrap2.css" rel="stylesheet">
    <link href="css/bootstrap2.min.css" rel="stylesheet">
    <%--<!-- Optional theme -->--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

    <!-- jquery datepicker ui css, used here for datepicker -->
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
    <link href="css/style2.css" rel="stylesheet">

    <style>
        body{
            margin-left: 1%;
            margin-top: 1%;
        }
        th, #counterColumn{
            text-align: center;
        }
        #description{
            padding: 3%;
        }
    </style>
</head>
<body>
    <%@ page import="java.io.*,java.sql.*"%>
    <%
        String assetTag = "'" + request.getParameter("pp_asset_tag") + "'";
        System.out.println(assetTag);
        String query = "Select pp_asset_tag, file, description from upload where pp_asset_tag = " + assetTag;

        String BookId =request.getParameter("pp_asset_tag");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/DriveTracker","fortrw","password");
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);

    %>
    <h4 >Asset Tag: <%=request.getParameter("pp_asset_tag")%> Files</h4>
    <table cellpadding="15" border="1">
        <tr>
            <th>No. </th>
            <th>Description</th>
        </tr>
            <td>
                <%
                    int i = 1;
                    while(rs.next()){
                %>
            </td>
        <tr>
            <td id="counterColumn">
                <%=i%>
            </td>
            <td id="description">
                <%--<div style="text-align: center">Asset Tag: <%=rs.getString("pp_asset_tag")%></div>--%>
                <div style="font-size: 17px;"><a href="<%="FileReadPdf?assetTag=" + rs.getString("pp_asset_tag") + "&" + "description=" + rs.getString("description")%>" target="_blank"><%=rs.getString("description")%></a></div>
            </td>
        </tr>
        <%
            i++;
            }%>
    </table>
</body>
</html>