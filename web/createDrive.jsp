<%@include file="navbar.jsp" %>
<style>
    .control-label{
        font-weight: bold;
    }
</style>

<br>
<h1 style="text-align: center;"><i class="icon-magic"></i> Create Drive</h1>
<h2 style="text-align: center;" id="printchatbox"></h2>
<br>

<hr>
<div class="sales">
    <div id="createDrive">
        <form style="margin-left: 31%" id="myForm" action="uploadServlet" class="form-horizontal" method="post" enctype="multipart/form-data">
            <div class="control-group">
                <label class="control-label" for="pp_asset_tag">PP Asset Tag</label>
                <div class="controls">
                    PS <input type="text" id="pp_asset_tag" pattern="\d{6}"  title="Only 6 digits" style="height: inherit;" placeholder="PP Asset Tag (6-digit Number)" required>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="customer_name">Customer Name</label>
                <div class="controls">
                    <input type="text" id="customer_name" style="height: inherit;"  placeholder="Customer Name">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="cts">CTS</label>
                <div class="controls">
                    <input type="text" id="cts" style="height: inherit;" placeholder="CTS">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="jira">JIRA</label>
                <div class="controls">
                    <input type="text" id="jira" style="height: inherit;" placeholder="JIRA (ex. PSP-xx)">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="manufacturer">Manufacturer/Model</label>
                <div class="controls">
                    <input type="text" style="height: inherit;" id="manufacturer" placeholder="Manufacturer/Model">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="serial_number">Serial Number</label>
                <div class="controls">
                    <input type="text" style="height: inherit;" id="serial_number" placeholder="Serial Number">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="label">Label</label>
                <div class="controls">
                    <input type="text" style="height: inherit;" id="label" placeholder="Label">
                </div>
            </div>


            <div class="control-group">
                <label class="control-label" for="usb">USB</label>
                <div class="controls">
                    <select id="usb">
                        <option value=""></option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="power">Power</label>
                <div class="controls">
                    <select id="power">
                        <option value=""></option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="property">Property Owner</label>
                <div class="controls">
                    <select id="property">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="drive_location">Drive Location</label>
                <div class="controls">
                    <select id="drive_location">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="drive_state">Drive Status</label> <%--Drive State is changed to Drive Status--%>
                <div class="controls">
                    <select id="drive_state">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="return_media_to_customer">Return Media To Customer</label>
                <div class="controls">
                    <select id="return_media_to_customer">
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                        <option value=""></option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="essential">Essentials</label>
                <div class="controls">
                    <select id="essential">
                        <option value="No">No</option>
                        <option value="Yes">Yes</option>
                        <option value=""></option>
                    </select>
                </div>

            </div>

            <input id="received_date" type="hidden">

            <div id="shipping_info">

                <div class="control-group">
                    <label class="control-label" for="sent_date">Sent Date</label>
                    <div class="controls">
                        <input type="text" id="sent_date">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="shipping_carrier">Shipping Carrier</label>
                    <div class="controls">
                        <select id="shipping_carrier">
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="shipping_tracking_number">Tracking Number</label>
                    <div class="controls">
                        <input type="text" id="shipping_tracking_number">
                    </div>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="notes">Notes</label>
                <div class="controls">
                    <textarea style="height: inherit;" id="notes"></textarea>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary btn-lg" id="create" value="save">Create</button>
                    <button type="submit" class="btn btn-danger btn-lg" onclick="myFunction()" value="Reset form">Reset</button>
                </div>
            </div>

            <div id="spinner" hidden><center><i class="icon-spinner icon-spin icon-3x"></i></center></div>

            <p id="error-message" class="text-error" hidden><b>Missing Value/s</b></p>
        </form>
    </div>

    <div id="page_spinner" hidden>
        <center><i class="icon-spinner icon-spin icon-3x"></i></center>
    </div>

    <div id="result">
    </div>

    <input id="username" value='<%= userName %>' hidden>
    <hr>

    <%@include file="footer.jsp" %>
</div>
<!-- /container -->

<script src="js/jquery.js"></script>
<script src="js/jquery-ui-1.8.13.custom.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bootbox.js"></script>

<script src="js/methods.js"></script>
<script src="js/locations.js"></script>
<script src="js/states.js"></script>
<script src="js/shipping_carriers.js"></script>
<script src="js/property.js"></script>

<script src="js/createDrive_script.js"></script>


<script>
    function myFunction() {
        document.getElementById("myForm").reset();
    }

    var inputBox = document.getElementById('customer_name');

    inputBox.onkeyup = function(){
        document.getElementById('printchatbox').innerHTML = inputBox.value;
    }
</script>
</body>
</html>
