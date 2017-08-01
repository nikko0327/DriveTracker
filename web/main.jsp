
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
<script src="js/libs/jquery.js"></script>
<script src="js/libs/jquery-ui-1.8.13.custom.min.js"></script>
<script src="js/libs/bootstrap.js"></script>
<script src="js/libs/jquery.tablesorter.js"></script>
<script src="js/libs/bootbox.js"></script>

<script>
    $(document).ready(function() {
        //Whenever one of the tabs are clicked make sure the rest are deactivated
        // and then add active to the one that was clicked
        $('.nav.nav-tabs > li').on('click', function (e) {
            e.preventDefault();
            $('.nav.nav-tabs > li').removeClass('active');
            $(this).addClass('active');

            var id = $(this).attr("id");

            $(".frame").hide();
            $("#f" + id).show();

        });

    });
</script>