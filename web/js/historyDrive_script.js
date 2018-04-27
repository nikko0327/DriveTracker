$(document).ready(function () {
    // just defining commonly used variable here
    var search_form = $('#search_form');
    var pp_asset_tag = $('#pp_asset_tag');

    pp_asset_tag.focus(); // highlight the pp_asset_tag search field
    $("[id$=date]").datepicker({dateFormat: "yy-mm-dd"}); // date picker initialization

    // when user submits the search form then execute searchDrive method (found below)
    search_form.on('submit', function (e) {
        e.preventDefault(); // prevent page from reloading when submitting
        searchDrive();
    });

    // need this because pp_asset_tag can be sent over GET form
    // to historyDrive.jsp from searchDrive by clicking on the asset_tag
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        var i;
        for (i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] === variable) { // if the variable in the url is variable, return its value
                return pair[1];
            }
        }
        return (false);
    }

    // if the pp_asset_tag GET field is not empty then
    // set the value of the search asset_tag search field
    // and submit the search query
    if (getQueryVariable("pp_asset_tag") !== ("" || false)) {
        pp_asset_tag.val(getQueryVariable("pp_asset_tag"));

        search_form.submit();
    }

    // automatically submit the form on page load
    search_form.submit();
});

function searchDrive() {
    var pp_asset_tag = $('#pp_asset_tag').val();
    var serial_number = $('#serial_number').val();
    var customer_name = $('#customer_name').val();
    var drive_state = $('#drive_state').val();
    var drive_location = $('#drive_location').val();
    var essential = $('#essential_select').val();
    var tableName = "drive_history";
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

            if (data.totalMatches == 0) {
                var msg = "No drives found for the selection, please try different combination or "
                    + "<a href='createDrive.jsp?pp_asset_tag=" + pp_asset_tag + "&"
                    + "serial_number=" + serial_number + "&"
                    + "customer_name=" + customer_name + "&"
                    + "update=false" + "'>create a drive</a>";

                $('#drive_list').append(msg);
            }
            else {
                var value = "<p>Total Matches: " + data.totalMatches + "</p>";
                value += "<table id='drive_table' class='table table-condensed table-hover tablesorter'>";
                value += "<thead>";
                value += "<tr style='background-color:#D8D8D8'>";
                value += "<th>Asset Tag</th>";
                value += "<th>Customer</th>";
                value += "<th>MFR/Model</th>";
                value += "<th>Serial</th>";
                value += "<th>Location</th>";
                value += "<th>Status</th>"; //State is changed to Status
                value += "<th>Return Media To Customer</th>";
                value += "<th>Created</th>";
                value += "<th>Updated</th>";
                value += "<th>Updater</th>";
                value += "<th>Notes</th>";
                value += "<th>Essentials</th>";
                value += "</tr>";
                value += "</thead>";
                value += "<tbody>";

                var i = 1;
                $.each(data.drives, function (k, v) {
                    value += "<tr class='detail' id='tr_" + i + "'>";
                    if (v.pp_asset_tag == undefined) {
                        value += "<td>" + "Error: " + v.message + "</td>";
                    } else {
                        value += "<td><a href='historyDrive.jsp?pp_asset_tag=" + v.pp_asset_tag + "'>" + v.pp_asset_tag + "</td>";
                        value += "<td>" + v.customer_name + "</td>";
                        value += "<td>" + v.manufacturer + "</td>";
                        value += "<td>" + v.serial_number + "</td>";
                        value += "<td>" + v.drive_location + "</td>";
                        value += "<td>" + v.drive_state + "</td>";

                        if ((v.return_media_to_customer) == 'Yes') {
                            // value += "<td style = 'color: green'>" + "<strong>" + v.return_media_to_customer + "</strong>" + "</td>";
                            value += "<td style = 'color: green; font-size: 120%;'>" + '<i class="fa fa-check">' + '</i>' + 'Yes' + "</td>";
                        } else {
                            // value += "<td style='color: red'>" +  "<strong>" + v.return_media_to_customer + "</strong>" + "</td>";
                            value += "<td style = 'color: red; font-size: 120%;'>" + '<i class="fa fa-times">' + '</i>' + 'No' + "</td>";
                        }

                        value += "<td>" + v.created + "</td>";
                        value += "<td>" + v.last_updated + "</td>";
                        value += "<td>" + v.updated_by + "</td>";
                        value += "<td>" + v.notes + "</td>";
                        value += "<td>" + v.essential + "</td>";

                        value +=
                            "<input type='hidden' id='pp_asset_tag_" + i + "' value='" + v.pp_asset_tag + "'>" +
                            "<input type='hidden' id='customer_name_" + i + "' value='" + v.customer_name + "'>" +
                            "<input type='hidden' id='manufacturer_" + i + "' value='" + v.manufacturer + "'>" +
                            "<input type='hidden' id='serial_number_" + i + "' value='" + v.serial_number + "'>" +
                            "<input type='hidden' id='property_" + i + "' value='" + v.property + "'>" +
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
                            "<input type='hidden' id='shipping_tracking_number_sent_" + i + "' value='" + v.shipping_tracking_number_sent + "'>" +
                            "<input type='hidden' id='created_" + i + "' value='" + v.created + "'>" +
                            "<input type='hidden' id='last_updated_" + i + "' value='" + v.last_updated + "'>" +
                            "<input type='hidden' id='updated_by_" + i + "' value='" + v.updated_by + "'>" +
                            "<input type='hidden' id='essential" + i + "' value='" + v.essential + "'>" +
                            "</td>";
                    }
                    value += "</tr>";
                    i++;
                });

                value += "</tbody>";
                value += "</table>";

                $('#drive_list').append(value);
            }

            // using tablesorter 3rd party API, this will make table sortable and look better
            $('#drive_table').tablesorter();

            $('#page_spinner').hide();
        }, "json");

    //return false prevents form from reloading/refreshing/going to other page
    return false;
}