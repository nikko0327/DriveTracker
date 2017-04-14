$(document).ready(function() {

    $('#pp_asset_tag').focus();
    $("[id$=date]").datepicker({ dateFormat: "yy-mm-dd"});

    if($.browser.msie) {
        $('.container').empty();

        var msg = "<div class='masthead'><h3 class='muted'>Drive Tracking Dashboard</h3></div><hr>";
        msg += "<p class='text-error'>This site is compatible with Chrome or Firefox browsers</p>";
        msg += "<br>";
        msg += "<p>Please download Chrome: <a href='https://www.google.com/intl/en/chrome/browser/'>Download</a></p>";
        msg += "<p>Please download Firefox: <a href='http://www.mozilla.org/en-US/firefox/new/'>Download</a></p>";

        $('.container').append(msg);

        return;
    }

    //Added for pp asset tag click search from searchDrive START
    function getQueryVariable(variable)
    {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return(false);
    }
    console.log("Asset tag: " + getQueryVariable("pp_asset_tag"));

    if(getQueryVariable("pp_asset_tag") != "") {
        $('#pp_asset_tag').val(getQueryVariable("pp_asset_tag"));

        $(document).ready(function() {
            $('#search_form').submit();
        });
    }

    //END

    $('#search_form').on('submit', searchDrive);

    $(document).ready(function() {
        $('#search_form').submit();
    });
});

function searchDrive() {
    var pp_asset_tag = $('#pp_asset_tag').val();
    var serial_number = $('#serial_number').val();
    var customer_name = $('#customer_name').val();
    var drive_state = $('#drive_state').val();
    var drive_location = $('#drive_location').val();
    var tableName = "drive_history";
    $('#page_spinner').show();

    $.post("DriveSearchServlet",
        {
            pp_asset_tag : pp_asset_tag,
            serial_number : serial_number,
            customer_name : customer_name,
            drive_state : drive_state,
            drive_location : drive_location,
            tableName : tableName
        },
        function(data) {
            $('#drive_list').empty();

            if(data.totalMatches == 0) {
                var msg = "No drives found for the selection, please try different combination or "
                    + "<a href='createDrive.jsp?pp_asset_tag=" + pp_asset_tag + "&"
                    + "serial_number=" + serial_number + "&"
                    + "customer_name=" + customer_name + "&"
                    + "update=false" + "'>create a drive</a>";

                $('#drive_list').append(msg);
            }
            else {
                var value = "<p>Total Matches: " + data.totalMatches +"</p>";
                value += "<table id='drive_table' class='table table-condensed table-hover tablesorter'>";
                value += "<thead>";
                value += "<tr style='background-color:#D8D8D8'>";
                value += "<th>PP Asset Tag</th>";
                value += "<th>Manufacturer</th>";
                value += "<th>Serial</th>";
                value += "<th>Customer</th>";
                value += "<th>Location</th>";
                value += "<th>Status</th>"; //State is changed to Status
                value += "<th>Created</th>";
                value += "<th>Updated</th>";
                value += "<th>Updater</th>";
                value += "<th>Note</th>";
                value += "<th>Return Media To Customer</th>";
                value += "</tr>";
                value += "</thead>";
                value += "<tbody>";

                var i = 1;
                $.each(data.drives, function(k,v) {
                    value += "<tr class='detail' id='tr_" + i + "'>";
                    if(v.pp_asset_tag == undefined) {
                        value += "<td>" + "Error: " + v.message + "</td>";
                    }
                    else {
                        value += "<td><a href='historyDrive.jsp?pp_asset_tag=" + v.pp_asset_tag + "'>" + v.pp_asset_tag + "</td>";
                        value += "<td>" + v.manufacturer + "</td>";
                        value += "<td>" + v.serial_number + "</td>";
                        value += "<td>" + v.customer_name + "</td>";
                        value += "<td>" + v.drive_location + "</td>";
                        value += "<td>" + v.drive_state + "</td>";
                        value += "<td>" + v.created + "</td>";
                        value += "<td>" + v.last_updated + "</td>";
                        value += "<td>" + v.updated_by + "</td>";
                        value += "<td>" + v.notes + "</td>";
                        if((v.return_media_to_customer) == 'Yes') {
                            // value += "<td style = 'color: green'>" + "<strong>" + v.return_media_to_customer + "</strong>" + "</td>";
                            value += "<td style = 'color: green; font-size: 120%;'>" + '<i class="fa fa-check">' + '</i>' + 'Yes' + "</td>";
                        } else {
                            // value += "<td style='color: red'>" +  "<strong>" + v.return_media_to_customer + "</strong>" + "</td>";
                            value += "<td style = 'color: red; font-size: 120%;'>" + '<i class="fa fa-times">' + '</i>' + 'No' + "</td>";
                        }

                        value +=
                            "<input type='hidden' id='pp_asset_tag_" + i + "' value='" + v.pp_asset_tag + "'>" +
                            "<input type='hidden' id='manufacturer_" + i + "' value='" + v.manufacturer +"'>" +
                            "<input type='hidden' id='serial_number_" + i + "' value='" + v.serial_number + "'>" +
                            "<input type='hidden' id='property_" + i + "' value='" + v.property + "'>" +
                            "<input type='hidden' id='customer_name_" + i +"' value='" + v.customer_name + "'>" +
                            "<input type='hidden' id='cts_" + i + "' value='" + v.cts + "'>" +
                            "<input type='hidden' id='jira_" + i + "' value='" + v.jira + "'>" +
                            "<input type='hidden' id='label_" + i + "' value='" + v.label + "'>" +
                            "<input type='hidden' id='drive_location_" + i + "' value='" + v.drive_location + "'>" +
                            "<input type='hidden' id='drive_state_" + i + "' value='" + v.drive_state + "'>" +
                            "<input type='hidden' id='encrypted_" + i + "' value='" + v.encrypted + "'>" +
                            "<input type='hidden' id='usb_" + i + "' value='" + v.usb + "'>" +
                            "<input type='hidden' id='power_" + i + "' value='" + v.power + "'>" +
                            "<input type='hidden' id='notes_" + i + "' value='" + v.notes + "'>" +
                            "<input type='hidden' id='received_date_" + i + "' value='" + v.received_date + "'>" +
                            "<input type='hidden' id='sent_date_" + i + "' value='" + v.sent_date + "'>" +
                            "<input type='hidden' id='shipping_carrier_sent_" + i + "' value='" + v.shipping_carrier_sent + "'>" +
                            "<input type='hidden' id='shipping_tracking_number_sent_" + i +"' value='" + v.shipping_tracking_number_sent + "'>" +
                            "<input type='hidden' id='created_" + i + "' value='" + v.created + "'>" +
                            "<input type='hidden' id='last_updated_" + i + "' value='" + v.last_updated + "'>" +
                            "<input type='hidden' id='updated_by_" + i + "' value='" + v.updated_by + "'>" +
                            "</td>";
                    }
                    value += "</tr>";

                    i++;
                });

                value += "</tbody>";
                value += "</table>";

                $('#drive_list').append(value);
            }


            //now giving sorting options on specific columns
            $('#drive_table').tablesorter(/*{
             headers:{
             14:{sorter:false}
             }
             }*/);

            $('#page_spinner').hide();

        }, "json");

    //return false prevents form from reloading/refreshing/going to other page
    return false;
}

function getValuesById(id) {
    pp_asset_tag = $('#pp_asset_tag_' + id).val();
    manufacturer = $('#manufacturer_' + id).val();
    serial_number = $('#serial_number_' + id).val();
    property = $('#property_' + id).val();
    customer_name = $('#customer_name_' + id).val();
    cts = $('#cts_' + id).val();
    jira = $('#jira_' + id).val();
    label = $('#label_' + id).val();
    drive_location = $('#drive_location_' + id).val();
    drive_state = $('#drive_state_' + id).val();
    encrypted = $('#encrypted_' + id).val();
    usb = $('#usb_' + id).val();
    power = $('#power_' + id).val();
    notes = $('#notes_' + id).val();
    return_media_to_customer = $('#return_media_to_customer' + id).val();
    received_date = $('#received_date_' + id).val();
    sent_date = $('#sent_date_' + id).val();
    shipping_carrier_sent = $('#shipping_carrier_sent_' + id).val();
    shipping_tracking_number_sent = $('#shipping_tracking_number_sent_' + id).val();
    created = $('#created_' + id).val();
    last_updated = $('#last_updated_' + id).val();
    updated_by = $('#updated_by_' + id).val();
}
