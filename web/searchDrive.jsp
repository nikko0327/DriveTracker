<%@include file="navbar.jsp" %>
<br>

<div id="search_options">
    <h1 style="text-align: center"><i class="icon-home"></i> Home</h1>
    <br>
    <form class="form-horizontal" id="search_form" style="text-align: center">
        <input placeholder ="PP Asset Tag" type="text" name="pp_asset_tag" id="pp_asset_tag" style="height: inherit">
        <input placeholder="Serial Number" type="text" name="serial_number" id="serial_number" style="height: inherit">
        <input placeholder ="Customer Name" type="text" name="customer_name" id="customer_name" style="height: inherit">
        <select id="drive_state">
            <option value ="" disabled selected style="display:none; color:gray;">Status</option>
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
        <button type="reset" class="btn btn-danger" id="myReset">Reset</button>
    </form>
    <div id="row_actions_update_box"></div>
</div>

<hr>

<div id="page_spinner" hidden>
    <center><i class="icon-spinner icon-spin icon-3x"></i></center>
</div>
<input type="button" value="Print this page" onClick="window.print()">
<p style="color: green;">Note: To export to pdf, change destination to "Save as PDF" <br>Layout: Landscape <br>Paper Size: Tabloid</p>
<div class="sales" id="drive_list" style="overflow: scroll; height: 800px"></div>

<!-- Start of udpate modal -->
<div id="updateModal" class="modal hide fade in" style="display: none;">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">?</a>
        <h3>Update Drive</h3>
        <div id="change_customer"></div>
    </div>
    <div class="modal-body">
        <div id="modal-alert-area" class="alert" hidden>

        </div>
        <form class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="modal_pp_asset_tag">PP Asset Tag</label>
                <div class="controls">
                    <input type="text" id="modal_pp_asset_tag" class="update_modal_input" value="" readonly>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_customer_name">Customer</label>
                <div class="controls">
                    <input style="color: black" type="text" id="modal_customer_name" class="update_modal_input" value="">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_cts">CTS</label>
                <div class="controls">
                    <input style="color: black" type="text" id="modal_cts" value="" class="update_modal_input">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_jira">JIRA</label>
                <div class="controls">
                    <input style="color: black" type="text" id="modal_jira" value="" class="update_modal_input">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_manufacturer">Manufacturer/Model</label>
                <div class="controls">
                    <input style="color: black" type="text" id="modal_manufacturer" value="" class="update_modal_input">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_serial_number">Serial Number</label>
                <div class="controls">
                    <input style="color: black" type="text" id="modal_serial_number" value="" class="update_modal_input">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_label">Label</label>
                <div class="controls">
                    <input style="color: black" type="text" id="modal_label" value="" class="update_modal_input">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_usb">USB</label>
                <div class="controls">
                    <select id="modal_usb" class="update_modal_select">
                        <option value=""></option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_power">Power</label>
                <div class="controls">
                    <select id="modal_power" class="update_modal_select">
                        <option value=""></option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_property">Property</label>
                <div class="controls">
                    <select id="modal_property" class="update_modal_select">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_drive_location">Drive Location</label>
                <div class="controls">
                    <select id="modal_drive_location" class="update_modal_select">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_drive_state">Drive Status</label> <%--Drive State is changed to Drive Status--%>
                <div class="controls">
                    <select id="modal_drive_state" class="update_modal_select">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_return_media_to_customer">Return Media To Customer</label>
                <div class="controls">
                    <select id="modal_return_media_to_customer" class="update_modal_select">
                        <option value=""></option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_essential">Essentials</label>
                <div class="controls">
                    <select id="modal_essential" class="update_modal_select">
                        <option value=""></option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
            </div>

            <div id="shipping_information_received">
                <div class="control-group">
                    <label class="control-label" for="modal_received_date">Received Date</label>
                    <div class="controls">
                        <input style="color: black" type="text" id="modal_received_date" value="" class="update_modal_input">
                    </div>
                </div>
            </div>

            <div id="shipping_information_sent">
                <div class="control-group">
                    <label class="control-label" for="modal_sent_date">Sent Date</label>
                    <div class="controls">
                        <input style="color: black" type="text" id="modal_sent_date" value="" class="update_modal_input">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="modal_shipping_carrier_sent">Shipping Carrier</label>
                    <div class="controls">
                        <select id="modal_shipping_carrier_sent" class="update_modal_select">
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="modal_shipping_tracking_number_sent">Shipping Tracking Number</label>
                    <div class="controls">
                        <input style="color: black" type="text" id="modal_shipping_tracking_number_sent" value=""  class="update_modal_input">
                    </div>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_notes">Notes</label>
                <div class="controls">
                    <textarea style="color: black" id="modal_notes"></textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="modal_file2">File Upload</label>
                <div class="controls">
                    <div id="modal_file2"></div>
                </div>
            </div>
            <div id="modal_spinner" hidden>
                <center><i class="icon-spinner icon-spin icon-3x"></i></center>
            </div>
        </form>
    </div>

    <div class="modal-footer">
        <a href="#" class="btn btn-success" id="modalUpdateButton">Update</a>
        <a href="#" class="btn" id="modalUpdateCloseButton" data-dismiss="modal">Close</a>
    </div>
</div>
<!-- end of udpate modal -->

<!-- Start of details modal -->
<div id="detailsModal" class="modal hide fade in" style="display: none;">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">?</a>
        <h3>Drive Details</h3>
        <div id="details_change_customer"></div>
    </div>

    <div class="modal-body">
        <form class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="details_modal_pp_asset_tag">PP Asset Tag</label>
                <div class="controls">
                    <p id="details_modal_pp_asset_tag" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_customer_name">Customer</label>
                <div class="controls">
                    <p id="details_modal_customer_name" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_manufacturer">Manufacturer/Model</label>
                <div class="controls">
                    <p id="details_modal_manufacturer" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_cts">CTS</label>
                <div class="controls">
                    <p id="details_modal_cts" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_jira">JIRA</label>
                <div class="controls">
                    <p id="details_modal_jira" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_serial_number">Serial Number</label>
                <div class="controls">
                    <p id="details_modal_serial_number" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_label">Label</label>
                <div class="controls">
                    <p id="details_modal_label" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_usb">USB</label>
                <div class="controls">
                    <p id="details_modal_usb" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_power">Power</label>
                <div class="controls">
                    <p id="details_modal_power" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_property">Property</label>
                <div class="controls">
                    <p id="details_modal_property" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_drive_location">Drive Location</label>
                <div class="controls">
                    <p id="details_modal_drive_location" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_drive_state">Drive Status</label>
                <div class="controls">
                    <p id="details_modal_drive_state" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_return_media_to_customer">Return Media To Customer</label>
                <div class="controls">
                    <p id="details_modal_return_media_to_customer" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_essential">Essentials</label>
                <div class="controls">
                    <p id="details_modal_essential" class="detail_modal_p"></p>
                </div>
            </div>

            <div id="details_shipping_information_received">
                <div class="control-group">
                    <label class="control-label" for="details_modal_received_date">Received Date</label>
                    <div class="controls">
                        <p id="details_modal_received_date" class="detail_modal_p"></p>
                    </div>
                </div>
            </div>

            <div id="details_shipping_information_sent">
                <div class="control-group">
                    <label class="control-label" for="modal_sent_date">Sent Date</label>
                    <div class="controls">
                        <p id="details_modal_sent_date" class="detail_modal_p"></p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="modal_shipping_carrier_sent">Shipping Carrier</label>
                    <div class="controls">
                        <p id="details_modal_shipping_carrier_sent" class="detail_modal_p"></p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="details_modal_shipping_tracking_number_sent">Shipping Tracking Number</label>
                    <div class="controls">
                        <p id="details_modal_shipping_tracking_number_sent" class="detail_modal_p"></p>
                    </div>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_notes">Notes</label>
                <div class="controls">
                    <p id="details_modal_notes" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_file">Files</label>
                <div class="controls">
                    <p id="details_modal_file" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_created">Created</label>
                <div class="controls">
                    <p id="details_modal_created" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_last_updated">Last Updated</label>
                <div class="controls">
                    <p id="details_modal_last_updated" class="detail_modal_p"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="details_modal_updated_by">Updated By</label>
                <div class="controls">
                    <p id="details_modal_updated_by" class="detail_modal_p"></p>
                </div>
            </div>

            <div id="details_modal_spinner" hidden>
                <center><i class="icon-spinner icon-spin icon-3x"></i></center>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">Close</a>
    </div>
</div>
<!-- end of details modal -->

<%@include file="footer.jsp" %>
<script src="js/searchDrive_script.js"></script>

</body>
</html>
