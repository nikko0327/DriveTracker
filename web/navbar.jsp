<%--
  Created by IntelliJ IDEA.
  User: nlee
  Date: 6/30/16
  Time: 8:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Drive Tracking Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet">
    <!--  <link href="css/datepicker.css" rel="stylesheet" type="text/css"> -->

    <!-- jquery datepicker ui css, used here for datepicker -->
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />

    <link href="css/font-awesome.css" rel="stylesheet">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">

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

    <link href="css/bootstrap-toggle.css" rel="stylesheet">
</head>

<body class="home">
    <style>
        #alert-area, #modal-alert-area {
            position: fixed;
            top: 5%;
            left: 50%;
            -webkit-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            border: 1px solid black;
            z-index: 999;
        }
    </style>
    <div id="alert-area" class="alert" hidden>

    </div>
    <div class="container-fluid display-table">
        <div class="row display-table-row">
            <div class="col-md-2 col-sm-1 hidden-xs display-table-cell v-align box" id="navigation">
                <div class="logo">
                    <a hef="home.html"><img style="max-width: 245px !important; margin-top: 6%;" src="https://wiki.proofpoint.com/wiki/download/attachments/425986/atl.site.logo?version=2&modificationDate=1393960799000&api=v2" alt="merkery_logo" class="hidden-xs hidden-sm">
                        <img style="max-width: 245px !important; margin-top: 6%;" src="https://wiki.proofpoint.com/wiki/download/attachments/425986/atl.site.logo?version=2&modificationDate=1393960799000&api=v2" alt="merkery_logo" class="visible-xs visible-sm circle-logo">
                    </a>
                </div>
                <div class="navi">
                    <ul>
                        <li><a href="searchDrive.jsp"><i class="fa fa-home" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Home</span></a></li>
                        <li><a href="createDrive.jsp"><i class="fa fa-hdd-o" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Create Drive</span></a></li>
                        <li><a href="historyDrive.jsp"><i class="fa fa-bar-chart" aria-hidden="true"></i><span class="hidden-xs hidden-sm">History</span></a></li>
                        <li><a target="_blank" href="https://proofpoint.onelogin.com/"><i class="fa fa-bar- fa-external-link" aria-hidden="true"></i><span class="hidden-xs hidden-sm">One Login</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-10 col-sm-11 display-table-cell v-align">
                <div class="row">
                    <header>
                        <div class="col-md-7">
                            <nav class="navbar-default pull-left">
                                <div class="navbar-header">
                                    <!--<button type="button" class="navbar-toggle collapsed" data-toggle="offcanvas" data-target="#side-menu" aria-expanded="false">!-->
                                    <button type="button" class="navbar-toggle collapsed" data-toggle="offcanvas" data-target="#side-menu" aria-expanded="false">
                                        <span class="sr-only">Toggle navigation</span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                </div>
                            </nav>
                            <div class="search hidden-xs hidden-sm">
                                <h1><strong>Drive Tracker</strong></h1>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="header-rightside">
                                <ul class="list-inline header-top pull-right">
                                    <li class="hidden-xs"><a class="btn btn-md btn-success" href="createDrive.jsp" role="button">Create Drive</a></li>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="https://cdn1.iconfinder.com/data/icons/mix-color-4/502/Untitled-1-512.png" alt="user">
                                            <b class="caret"></b></a>
                                        <ul class="dropdown-menu" id="user_dropdown">
                                            <li>
                                                <div class="navbar-content">
                                                    <p id="user_name" class="text-muted small" style="margin-bottom: 0px">
                                                    </p>
                                                    <p id="group_name" class="text-muted small" style="margin-bottom: 0px">
                                                    </p>
                                                    <label>Notifications: </label><input id="notification_button" checked type="checkbox">
                                                    <div class="divider"></div>
                                                    <a href="logout.jsp" class="view btn-sm active">Logout</a>
                                                </div>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </header>
                </div>
