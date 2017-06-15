<%@include file="navbar.jsp" %>

<script>
    function myFunction() {
        document.getElementById("search_form").reset();
    }
</script>

<br>

<div id="search_options">
    <h1 style="text-align: center"><i class="icon-time"></i> Drive History</h1>
    <br>
    <form class="form-horizontal" id="search_form" style="text-align: center">
        <input placeholder ="PP Asset Tag" type="text" name="pp_asset_tag" id="pp_asset_tag" style="height: inherit">
        <input placeholder="Serial Number" type="text" name="serial_number" id="serial_number" style="height: inherit">
        <input placeholder ="Customer Name" type="text" name="customer_name" id="customer_name" style="height: inherit">

        <select id="drive_state">
            <option value ="" disabled selected style="display:none; color:gray;">Status</option> <%--Drive State is changed to Drive Status--%>
        </select>

        <select id="drive_location">
            <option value ="" disabled selected style="display:none; color:gray;">Location</option>
        </select>

        <select id="essential_select">
            <option value ="">Essentials</option>
            <option value ="Yes">Yes</option>
            <option value ="No">No</option>
        </select>
        <br>
        <br>
        <button type="submit" class="btn btn-primary" id="search">Search</button>
        <button type="submit" class="btn btn-danger btn-md" onclick="myFunction()" value="Reset form">Reset</button>

    </form>
</div>

<hr>

<div class="sales">
    <div id="page_spinner" hidden>
        <center><i class="icon-spinner icon-spin icon-3x"></i></center>
    </div>
    <input type="button" value="Print this page" onClick="window.print()">
    <p style="color: green;">Note: To export to pdf, change destination to "Save as PDF" <br>Layout: Landscape <br>Paper Size: Tabloid</p>
    <div id="drive_list" style="overflow: scroll; height: 800px"></div>
    <input id="username" value='<%= userName %>' hidden>
    <hr>
    <%@include file="footer.jsp" %>
</div>


<script src="js/jquery.js"></script>
<script src="js/jquery-ui-1.8.13.custom.min.js"></script>
<script src="js/bootstrap-transition.js"></script>
<script src="js/bootstrap-alert.js"></script>
<script src="js/bootstrap-modal.js"></script>
<script src="js/bootstrap-dropdown.js"></script>
<script src="js/bootstrap-scrollspy.js"></script>
<script src="js/bootstrap-tab.js"></script>
<script src="js/bootstrap-tooltip.js"></script>
<script src="js/bootstrap-popover.js"></script>
<script src="js/bootstrap-button.js"></script>
<script src="js/bootstrap-collapse.js"></script>
<script src="js/bootstrap-carousel.js"></script>
<script src="js/bootstrap-typeahead.js"></script>
<script src="js/jquery.tablesorter.js"></script>
<script src="js/bootbox.js"></script>
<script src="js/property.js"></script>
<script src="js/locations.js"></script>
<script src="js/states.js"></script>
<script src="js/shipping_carriers.js"></script>

<script src="js/historyDrive_script.js"></script>
</body>
</html>
