$(document).ready(function () {
    var user = new User();
    var username = user.getUsername();
    var msgEnum = new Enum().MESSAGE;
    var action_alert_box = new Alert($('#alert-area'));
    var modal_alert = new Alert($('#modal-alert-area'));
    new Clipboard('.clipbtn');


    $('#pp_asset_tag').focus();
    $("[id$=date]").datepicker({dateFormat: "yy-mm-dd"});

    /* Begin search bar logic */
    var search_form = $('#search_form');
    search_form.on('submit', function (e) {
        e.preventDefault();
        searchDrive(user);
    });

    search_form.on('reset', function () {
        search_form[0].reset();
        $('#pp_asset_tag').val("");
        $('#serial_number').val("");
        $('#customer_name').val("");
        $('#drive_state').val("");
        $('#drive_location').val("");
        $('#essential').val("");
        searchDrive(user);
    });

    search_form.submit();
    /* End search bar logic */

    // when user clicks on a link (<a>) stop the propagation
    // so that it doesnt activate other events related to the drive_table
    $(document).on('click', '#drive_table a', function (e) {
        e.stopPropagation();
    });

    // when user clicks on one of the table rows then show them details of the drive
    $(document).on('click', '#drive_table tr', function () {
        //get id # of table row
        var id = $(this).attr('id').replace('tr_', '');
        //assign the values to values
        var values = getValuesById(id);

        $('#details_change_customer').html("<a href='createDrive.jsp?pp_asset_tag=" + values.pp_asset_tag + "&" +
            "manufacturer=" + values.manufacturer + "&" +
            "serial_number=" + values.serial_number + "&" +
            "update=true" + "'>Click here to use this Drive for other customer</a>");
        $('#details_modal_pp_asset_tag').html(values.pp_asset_tag);
        $('#details_modal_manufacturer').html(values.manufacturer);
        $('#details_modal_serial_number').html(values.serial_number);
        $('#details_modal_property').html(values.property);
        $('#details_modal_customer_name').html(values.customer_name);
        $('#details_modal_cts').html(generateCTSTicketUrl(values.cts));
        $('#details_modal_jira').html(generateJIRATicketUrl(values.jira));
        $('#details_modal_label').html(values.label);
        $('#details_modal_drive_location').html(values.drive_location);
        $('#details_modal_drive_state').html(values.drive_state);
        $('#details_modal_return_media_to_customer').html(values.return_media_to_customer);
        $('#details_modal_essential').html(values.essential);
        $('#details_modal_encrypted').html(values.encrypted);
        $('#details_modal_usb').html(values.usb);
        $('#details_modal_power').html(values.power);
        $('#details_modal_notes').html(values.notes);

        $.ajax({
            url: "upload2.jsp?pp_asset_tag=" + values.pp_asset_tag + "&update=false",
            dataType: "text",
            success: function (msg) {
                $('#details_modal_file').append(msg);
            }
        });
        $('#details_modal_received_date').html(values.received_date);
        $('#details_modal_sent_date').html(values.sent_date);
        $('#details_modal_shipping_carrier_sent').html(values.shipping_carrier_sent);
        $('#details_modal_shipping_tracking_number_sent')
            .html(generateShippingTrackingNumberUrl(values.shipping_tracking_number_sent, values.shipping_carrier_sent));
        $('#details_modal_created').html(values.created);
        $('#details_modal_last_updated').html(values.last_updated);
        $('#details_modal_updated_by').html(values.updated_by);

        $('#details_modal_spinner').hide();
        $('#detailsModal').modal();
    });

    //Prevents duplicate upload tables from being presented in the modal in case they open up the update again
    $("#detailsModal").on("hidden", function () {
        $('#details_modal_file').children().remove();
    });

    // when user clicks on the delete (trash can) button
    $(document).on('click', 'button[name="deleteButton"]', function (e) {
        var id = $(this).attr('id').replace('delete_', '');
        var pp_asset_tag = $('#pp_asset_tag_' + id).val();
        var customer_name = $('#customer_name_' + id).val();
        var essential = $('#essential_' + id).val();

        bootbox.confirm("Are you sure you want to delete this drive?",
            function (result) {
                if (result === true) {
                    user.isAdmin(function (isAnAdmin) {
                        if (isAnAdmin) {
                            $.post("deleteDrive",
                                {
                                    pp_asset_tag: pp_asset_tag,
                                    customer_name: customer_name,
                                    updated_by: username,
                                    essential: essential
                                },
                                function (data) {
                                    if (data.result === 'success') {
                                        $('#tr_' + id).remove();
                                        action_alert_box.displayMessage(pp_asset_tag + " has been deleted!", msgEnum.props[msgEnum.SUCCESS].class_code,
                                            msgEnum.props[msgEnum.SUCCESS].first_word, msgEnum.props[msgEnum.SUCCESS].ttl)
                                    }
                                    else {
                                        action_alert_box.displayMessage("Error: " + data.result, msgEnum.props[msgEnum.FAILURE].class_code,
                                            msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
                                    }
                                    $('#modal_spinner').hide();
                                }, 'json');
                        }
                        else
                            alert("You must be an admin to delete a row");
                    });
                }
            });
        //prevents other buttons from being clicked when this one is
        e.stopPropagation();
    });

    // when user clicks on the copy (two intersecting squares) button
    $(document).on('click', 'button[name="copyButton"]', function (e) {
        //prevents other operations from being clicked at same time
        e.stopPropagation();
    });

    // when user clicks the update button
    $(document).on('click', "button[name='updateButton']", function (e) {
        var id = $(this).attr('id').replace('update_', '');
        var values = getValuesById(id);

        $('#change_customer').html("<a href='createDrive.jsp?pp_asset_tag=" + values.pp_asset_tag + "&" +
            "manufacturer=" + values.manufacturer + "&" +
            "serial_number=" + values.serial_number + "&" +
            "update=true" + "'>Click here to use this Drive for other customer</a>");
        $('#modal_pp_asset_tag').val(values.pp_asset_tag);
        $('#modal_manufacturer').val(values.manufacturer);
        $('#modal_serial_number').val(values.serial_number);

        $('#modal_property').val(values.property);
        $('#modal_property').change();//same as .trigger("change")

        $('#modal_customer_name').val(values.customer_name);
        $('#modal_cts').val(values.cts);
        $('#modal_jira').val(values.jira);
        $('#modal_label').val(values.label);
        $('#modal_drive_location').val(values.drive_location);
        $('#modal_drive_state').val(values.drive_state);
        $('#modal_return_media_to_customer').val(values.return_media_to_customer);
        $('#modal_essential').val(values.essential);
        $('#modal_encrypted').val(values.encrypted);
        $('#modal_usb').val(values.usb);
        $('#modal_power').val(values.power);
        $('#modal_notes').val(values.notes);
        $('#modal_received_date').val(values.received_date);
        $('#modal_sent_date').val(values.sent_date);
        $('#modal_shipping_carrier_sent').val(values.shipping_carrier_sent);
        $('#modal_shipping_tracking_number_sent').val(values.shipping_tracking_number_sent);

        //Use ajax to load in data from the MySQL server using upload2.jsp
        //Default so its GET method
        $.ajax({
            url: "upload2.jsp?pp_asset_tag=" + values.pp_asset_tag + "&customer_name=" + values.customer_name + "&update=true",
            dataType: "text",
            success: function (msg) {
                $('#modal_file2').append(msg);
                //The Asset Tag must be changed here because there is no guarantee ajax will finish loading before it gets to the next line
                $('#assetTag').val(values.pp_asset_tag);

            }
        });

        $('#modal_spinner').hide();
        $('#updateModal').modal();

        e.stopPropagation();
    });

    //Note: if the file is too large it will take a long time to upload
    $(document).on("click", "#upload", function (e) {
        e.stopPropagation();
        e.preventDefault();

        if ($("#file")[0].files[0] == null) {
            modal_alert.displayMessage("You need to choose a PDF file to upload!", msgEnum.props[msgEnum.FAILURE].class_code,
                msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
            return;
        }
        else if ($("#desc").val() == null || $("#desc").val() == "") {
            modal_alert.displayMessage("You need to include a short description!", msgEnum.props[msgEnum.FAILURE].class_code,
                msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
            return;
        }

        var asset_tag = $("#modal_pp_asset_tag").val();
        var desc = $("#desc").val();
        var customer_name = $('#modal_customer_name').val();

        var data = new FormData();
        data.append('assetTag', asset_tag);
        data.append('description', desc);
        data.append('file', $("#file")[0].files[0]);

        $('#modal_spinner').show();

        $.ajax({
            url: "FileUploadDBServlet",
            type: "POST",
            data: data,
            cache: false,
            dataType: "json",
            processData: false,
            contentType: false,
            success: function (data) {
                $('#modal_spinner').hide();
                if (data.result === "success") {//If the data passes all the tests then update the page!
                    $.ajax({
                        url: "upload2.jsp?pp_asset_tag=" + asset_tag + "&customer_name=" + customer_name + "&update=true",
                        dataType: "text",
                        success: function (msg) {
                            $('#modal_file2').children().remove();
                            $('#modal_file2').append(msg);
                            modal_alert.displayMessage("File was uploaded to the server!", msgEnum.props[msgEnum.SUCCESS].class_code,
                                msgEnum.props[msgEnum.SUCCESS].first_word, msgEnum.props[msgEnum.SUCCESS].ttl);
                        }
                    });
                }
                else {
                    modal_alert.displayMessage("Error: " + data.result, msgEnum.props[msgEnum.FAILURE].class_code,
                        msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {//if the server gives us an error we're fucked
                $('#modal_spinner').hide();
                modal_alert.displayMessage("Error: " + errorThrown, msgEnum.props[msgEnum.FAILURE].class_code,
                    msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
            }
        });
    });

    //Prevents duplicate upload tables from being presented in the modal in case they open up the update again
    $("#updateModal").on("hidden", function () {
        $('#modal_file2').children().remove();
        $(".alert").hide();
    });

    // when user clicks on the modalUpdateButton event
    $(document).on('click', '#modalUpdateButton', function () {
        var pp_asset_tag = $('#modal_pp_asset_tag').val();
        var manufacturer = $('#modal_manufacturer').val();
        var serial_number = $('#modal_serial_number').val();
        var property = $('#modal_property').val();
        var customer_name = $('#modal_customer_name').val();
        var cts = $('#modal_cts').val();
        var jira = $('#modal_jira').val();
        var label = $('#modal_label').val();
        var drive_location = $('#modal_drive_location').val();
        var drive_state = $('#modal_drive_state').val();
        var return_media_to_customer = $('#modal_return_media_to_customer').val();
        var encrypted = $('#modal_encrypted').val();
        var usb = $('#modal_usb').val();
        var power = $('#modal_power').val();
        var notes = $('#modal_notes').val();
        var received_date = $('#modal_received_date').val();
        var sent_date = $('#modal_sent_date').val();
        var shipping_carrier_sent = $('#modal_shipping_carrier_sent').val();
        var shipping_tracking_number_sent = $('#modal_shipping_tracking_number_sent').val();
        var essential = $('#modal_essential').val();

        if (pp_asset_tag === null || pp_asset_tag === "") {
            alert("Drive must have a PP Asset Tag");
            $("#modal_pp_asset_tag").focus();
            return;
        }

        $('#modal_spinner').show();

        $.post("updateDrive",
            {
                pp_asset_tag: pp_asset_tag,
                manufacturer: manufacturer,
                serial_number: serial_number,
                property: property,
                customer_name: customer_name,
                cts: cts,
                jira: jira,
                label: label,
                drive_location: drive_location,
                drive_state: drive_state,
                return_media_to_customer: return_media_to_customer,
                encrypted: encrypted,
                usb: usb,
                power: power,
                notes: notes,
                received_date: received_date,
                sent_date: sent_date,
                shipping_carrier_sent: shipping_carrier_sent,
                shipping_tracking_number_sent: shipping_tracking_number_sent,
                updated_by: username,
                essential: essential
            },
            function (data) {
                $('#modal_spinner').hide();

                if (data.result === 'success') {
                    $('[data-dismiss="modal"]').click();
                    $('#search').click();
                    //location.reload();
                }
                else
                    alert("Error: " + data.result);
            }, 'json');
    });

    // when user clicks on deletePDFButton event
    $(document).on('click', "button[name='deletePDFButton']", function (e) {
        e.stopPropagation();
        e.preventDefault();
        var id = this.getAttribute("id");
        id = id.replace("deletePDF_", "");
        var modal_spinner = $('#modal_spinner');

        user.isAdmin(function (isAnAdmin) {
            if (isAnAdmin) {
                bootbox.confirm("Are you sure you want to delete this file?", function (result) {
                    if (result === true) {
                        modal_spinner.show();
                        $.ajax({
                            url: "deleteFile",
                            type: 'POST',
                            data: {id: id},
                            dataType: "json",
                            success: function (data) {
                                modal_spinner.hide();
                                if (data.result === 'success') {
                                    $('#fileID' + id).closest('tr').remove();
                                    modal_alert.displayMessage("Deleted file successfully.", msgEnum.props[msgEnum.SUCCESS].class_code,
                                        msgEnum.props[msgEnum.SUCCESS].first_word, msgEnum.props[msgEnum.SUCCESS].ttl);
                                }
                                else {
                                    modal_alert.displayMessage("Failed to delete the file: " + data.result, msgEnum.props[msgEnum.FAILURE].class_code,
                                        msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
                                }
                            },
                            error: function (e) {
                                modal_spinner.hide();
                                modal_alert.displayMessage("Something went wrong: " + e, msgEnum.props[msgEnum.FAILURE].class_code,
                                    msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
                            }
                        });
                    }
                });
            }
            else {
                modal_alert.displayMessage("You must be an admin to do this operation!", msgEnum.props[msgEnum.FAILURE].class_code,
                    msgEnum.props[msgEnum.FAILURE].first_word, msgEnum.props[msgEnum.FAILURE].ttl);
            }
        });
    });
});

function searchDrive(user) {
    //search values
    var pp_asset_tag = $('#pp_asset_tag').val();
    var serial_number = $('#serial_number').val();
    var customer_name = $('#customer_name').val();
    var drive_state = $('#drive_state').val();
    var drive_location = $('#drive_location').val();
    var essential = $('#essential_select').val();
    var tableName = "drive_info";

    $('#page_spinner').show();

    $.post("DriveSearchServlet",
        {
            pp_asset_tag: pp_asset_tag,
            serial_number: serial_number,
            customer_name: customer_name,
            drive_state: drive_state,
            drive_location: drive_location,
            tableName: tableName,
            essential: essential
        },
        function (data) {
            $('#drive_list').empty();

            if (data.totalMatches === 0) {
                var msg = "No drives found for the selection, please try different combination or "
                    + "<a href='createDrive.jsp?pp_asset_tag=" + pp_asset_tag + "&"
                    + "serial_number=" + serial_number + "&"
                    + "customer_name=" + customer_name + "&"
                    + "update=false" + "'>create a drive</a>";

                $('#drive_list').append(msg);
            }
            else {
                var value = "<p>Total Matches: " + data.totalMatches + "</p>";
                value += "<table id='drive_table' class='table table-condensed table-hover tablesorter' style='table-layout: auto;'>";
                value += "<thead>";
                value += "<tr style='background-color:#D8D8D8'>";
                value += "<th>Asset Tag</th>";
                value += "<th>Customer</th>";
                value += "<th>CTS</th>";
                value += "<th>JIRA</th>";
                value += "<th>MFR/Model</th>";
                value += "<th>Serial</th>";
                value += "<th>Label</th>";
                value += "<th>Location</th>";
                value += "<th>Property</th>";
                value += "<th>Status</th>";
                value += "<th>Return Media To Customer</th>";
                value += "<th>Essentials</th>";
                value += "<th>Updated</th>";
                value += "<th>Updated By</th>";
                value += "<th>Operations</th>";
                value += "</tr>";
                value += "</thead>";
                value += "<tbody>";

                var i = 1;
                $.each(data.drives, function (k, v) {
                    value += "<tr class='detail' id='tr_" + i + "'>";
                    if (v.pp_asset_tag === undefined) {
                        value += "<td>" + "Error: " + v.message + "</td>";
                    }
                    else {
                        value += "<td><a href='historyDrive.jsp?pp_asset_tag=" + v.pp_asset_tag + "'>" + v.pp_asset_tag + "</td>";
                        value += "<td>" + v.customer_name + "</td>";
                        value += "<td>" + generateCTSTicketUrl(v.cts) + "</td>";
                        value += "<td>" + generateJIRATicketUrl(v.jira) + "</td>";
                        value += "<td>" + v.manufacturer + "</td>";
                        value += "<td>" + v.serial_number + "</td>";
                        value += "<td>" + v.label + "</td>";
                        value += "<td>" + v.drive_location + "</td>";
                        value += "<td>" + v.property + "</td>";
                        value += "<td>" + v.drive_state + "</td>";

                        var clipboard_text = "" + v.pp_asset_tag + " " + v.customer_name + " " + v.cts + " " + v.jira +
                            " " + v.manufacturer + " " + v.serial_number;

                        if ((v.return_media_to_customer) == 'Yes') {
                            value += "<td style = 'color: green; font-size: 120%;'>" + '<i class="fa fa-check">' + '</i>' + 'Yes' + "</td>";
                        }
                        else {
                            value += "<td style = 'color: red; font-size: 120%;'>" + '<i class="fa fa-times">' + '</i>' + 'No' + "</td>";
                        }

                        value += "<td>" + v.essential + "</td>";
                        value += "<td>" + v.last_updated + "</td>";
                        value += "<td>" + v.updated_by + "</td>";

                        value += "<td id='ops_" + i + "' style='white-space: nowrap'><button name='updateButton' class='btn btn-sm btn-default' id='update_" + i + "'><i class='icon-edit'></i></button>";

                        value += "&nbsp;<button name='copyButton' class='btn btn-sm btn-default clipbtn' " +
                            "data-clipboard-text='" + clipboard_text + "' " +
                            "id='copy_" + i + "'><i class='icon-copy'></i></button>";


                        // check if user is an admin before giving them access to the delete/trashcan button
                        generateTrashCan(user, i);

                        value +=
                            "<input type='hidden' id='pp_asset_tag_" + i + "' value='" + v.pp_asset_tag + "'>" +
                            "<input type='hidden' id='manufacturer_" + i + "' value='" + v.manufacturer + "'>" +
                            "<input type='hidden' id='serial_number_" + i + "' value='" + v.serial_number + "'>" +
                            "<input type='hidden' id='property_" + i + "' value='" + v.property + "'>" +
                            "<input type='hidden' id='customer_name_" + i + "' value='" + v.customer_name + "'>" +
                            "<input type='hidden' id='cts_" + i + "' value='" + v.cts + "'>" +
                            "<input type='hidden' id='jira_" + i + "' value='" + v.jira + "'>" +
                            "<input type='hidden' id='label_" + i + "' value='" + v.label + "'>" +
                            "<input type='hidden' id='drive_location_" + i + "' value='" + v.drive_location + "'>" +
                            "<input type='hidden' id='drive_state_" + i + "' value='" + v.drive_state + "'>" +
                            "<input type='hidden' id='return_media_to_customer" + i + "' value='" + v.return_media_to_customer + "'>" +
                            "<input type='hidden' id='encrypted_" + i + "' value='" + v.encrypted + "'>" +
                            "<input type='hidden' id='usb_" + i + "' value='" + v.usb + "'>" +
                            "<input type='hidden' id='power_" + i + "' value='" + v.power + "'>" +
                            "<input type='hidden' id='notes_" + i + "' value='" + v.notes + "'>" +
                            "<input type='hidden' id='received_date_" + i + "' value='" + v.received_date + "'>" +
                            "<input type='hidden' id='sent_date_" + i + "' value='" + v.sent_date + "'>" +
                            "<input type='hidden' id='shipping_carrier_sent_" + i + "' value='" + v.shipping_carrier_sent + "'>" +
                            "<input type='hidden' id='shipping_tracking_number_sent_" + i + "' value='" + v.shipping_tracking_number_sent + "'>" +
                            "<input type='hidden' id='created_" + i + "' value='" + v.created + "'>" +
                            "<input type='hidden' id='last_updated_" + i + "' value='" + v.last_updated + "'>" +
                            "<input type='hidden' id='updated_by_" + i + "' value='" + v.updated_by + "'>" +
                            "<input type='hidden' id='essential_" + i + "' value='" + v.essential + "'>" +
                            "</td>";
                    }
                    value += "</tr>";

                    i++;
                });

                value += "</tbody>";
                value += "</table>";

                $('#drive_list').append(value);
            } // table creation

            //http://tablesorter.com/docs
            //third party table sorter utilized
            $('#drive_table').tablesorter();
            $('#page_spinner').hide();

        }, "json");

    //return false prevents form from reloading/refreshing/going to other page
    return false;
}

function getValuesById(id) {
    var pp_asset_tag = $('#pp_asset_tag_' + id).val();
    var manufacturer = $('#manufacturer_' + id).val();
    var serial_number = $('#serial_number_' + id).val();
    var property = $('#property_' + id).val();
    var customer_name = $('#customer_name_' + id).val();
    var cts = $('#cts_' + id).val();
    var jira = $('#jira_' + id).val();
    var label = $('#label_' + id).val();
    var drive_location = $('#drive_location_' + id).val();
    var drive_state = $('#drive_state_' + id).val();
    var return_media_to_customer = $('#return_media_to_customer' + id).val();
    var encrypted = $('#encrypted_' + id).val();
    var usb = $('#usb_' + id).val();
    var power = $('#power_' + id).val();
    var notes = $('#notes_' + id).val();
    var received_date = $('#received_date_' + id).val();
    var sent_date = $('#sent_date_' + id).val();
    var shipping_carrier_sent = $('#shipping_carrier_sent_' + id).val();
    var shipping_tracking_number_sent = $('#shipping_tracking_number_sent_' + id).val();
    var created = $('#created_' + id).val();
    var last_updated = $('#last_updated_' + id).val();
    var updated_by = $('#updated_by_' + id).val();
    var essential = $('#essential_' + id).val();

    return {
        pp_asset_tag: pp_asset_tag, manufacturer: manufacturer, serial_number: serial_number, property: property,
        customer_name: customer_name, cts: cts, jira: jira, label: label, drive_location: drive_location,
        drive_state: drive_state, return_media_to_customer: return_media_to_customer, encrypted: encrypted,
        usb: usb, power: power, notes: notes, received_date: received_date, sent_date: sent_date, shipping_carrier_sent:
        shipping_carrier_sent, shipping_tracking_number_sent: shipping_tracking_number_sent, created: created,
        last_updated: last_updated, updated_by: updated_by, essential: essential
    }; //23 values
}


function generateTrashCan(user, i) {
    user.isAdmin(function (isAnAdmin) {
        if (isAnAdmin) {
            var trash = "&nbsp;<button name='deleteButton' class='btn btn-sm btn-danger' id='delete_" + i + "'><i class='icon-trash'></i></button>";
            $('#ops_' + i).append(trash);
        }
    });
}

function generateCTSTicketUrl(cts) {
    var ticketUrl = "<a href='https://support.proofpoint.com/show_call.cgi?id=" + cts + "' target=_blank'>" + cts + "</a>";

    return ticketUrl;
}

function generateJIRATicketUrl(jira) {
    var ticketUrl = "<a href='https://jira.proofpoint.com/jira/browse/" + jira + "' target='_blank'>" + jira + "</a>";

    return ticketUrl;
}

function generateShippingTrackingNumberUrl(shipping_tracking_number, shipping_carrier) {
    if (shipping_tracking_number !== "") {
        var trackingUrl;
        if (shipping_carrier === "FedEx")
            trackingUrl = 'https://www.fedex.com/fedextrack/index.html?tracknumbers=' + shipping_tracking_number;
        else if (shipping_carrier === "UPS")
            trackingUrl = 'http://wwwapps.ups.com/WebTracking/processInputRequest?tracknum=' + shipping_tracking_number;
        else if (shipping_carrier === "USPS")
            trackingUrl = 'https://tools.usps.com/go/TrackConfirmAction.action?tLabels=' + shipping_tracking_number;
        else
            trackingUrl = "#";

        return "<a href='" + trackingUrl + "' target='_blank'>" + shipping_tracking_number + "</a>";
    }
    else
        return shipping_tracking_number;
}