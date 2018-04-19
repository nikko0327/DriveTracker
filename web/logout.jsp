<%@ page contentType="text/html; charset=UTF-8" %>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
      integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
      integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>

<html>
<head>
    <title>Logout</title>

    <style>
        body {
            background: url('img/pflogout.jpeg') no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            background-size: cover;
            -o-background-size: cover;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="masthead">
        <p class="pull-right"><img src="img/proofpoint_logo.png" alt="Proofpoint Logo"/></p>
    </div>
    <h1>You've successfully logged out.</h1>
    <h2>Click <a href="/DriveTracker"/>here</a> to log in again.</h2>
</div>

<!-- /container -->

<script language="JavaScript" type="text/javascript">
    window.history.forward(0);
</script>
<script src="js/libs/jquery.js"></script>
<script src="js/libs/bootstrap.js"></script>
<script src="js/libs/bootbox.js"></script>
<script src="js/logout_script.js"></script>
</body>
</html>