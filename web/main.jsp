
<head>
    <meta charset="utf-8">
    <title>Dashboard</title>

    <link href="css/bootstrap.css" rel="stylesheet">

    <link href="css/font-awesome.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-default" style="background-color: #0e1a35;">
        <div>
            <ul class="nav nav-tabs">
                <li role="presentation" class="active" id="drivetracker"><a href="#">DriveTracker</a></li>
                <li role="presentation" id="etacalculator"><a href="#">EtaCalculator</a></li>
                <li role="presentation" id="importexport"><a href="#">Import/Export</a></li>
            </ul>
        </div>
    </nav>
    <iframe class="frame" id="fdrivetracker" src="searchDrive.jsp" width="100%" height="100%"></iframe>
    <iframe class="frame" id="fetacalculator" src="historyDrive.jsp" width="100%" height="100%" hidden></iframe>
    <iframe class="frame" id="fimportexport" src="main.jsp" width="100%" height="100%" hidden></iframe>
</body>


</div>
<script src="js/jquery.js"></script>
<script src="js/jquery-ui-1.8.13.custom.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jquery.tablesorter.js"></script>
<script src="js/bootbox.js"></script>

<script src="js/tabs.js"></script>