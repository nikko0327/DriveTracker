
$(document).ready(function() {

    $('#pp_asset_tag').focus();
    $('#sent_date').datepicker({ dateFormat: "yy-mm-dd"});
    $('#received_date').datepicker({ dateFormat: "yy-mm-dd"}).datepicker('setDate', new Date());

    if($.browser.msie) {
        $('.container').empty();

        var msg = "<div class='masthead'><h3 class='muted'>Drive Tracking Dasboard</h3></div><hr>";
        msg += "<p class='text-error'>This site is compatible with Chrome or Firefox browsers</p>";
        msg += "<br>";
        msg += "<p>Please download Chrome: <a href='https://www.google.com/intl/en/chrome/browser/'>Download</a></p>";
        msg += "<p>Please download Firefox: <a href='http://www.mozilla.org/en-US/firefox/new/'>Download</a></p>";

        $('.container').append(msg);

        return;
    }

    $.urlParam = function(name) {
        return getFromURL(name);
    }

    is_update = $.urlParam("update");

    // If updating a existing drive
    // Then make PP Asset Tag uneditable
    if(is_update != "") {
        $('#pp_asset_tag').val($.urlParam("pp_asset_tag").substring(2)); // Remove the prefix "PS"
        $('#pp_asset_tag').attr('readonly', true);

        if(is_update == "true") {
            $('#manufacturer').val($.urlParam("manufacturer"));
            $('#manufacturer').attr('readonly', true);

            $('#serial_number').val($.urlParam("serial_number"));
            $('#serial_number').attr('readonly', true);

            $('#customer_name').focus();
        }
        else
            $('#manufacturer').focus();
    }

    // If Property = Customer
    // Then Return Media to Customer default to Yes
    //
    // If Property = Proofpoint
    // Then Return Media to Customer default to NO
    $(document).on('change', '#property', function() {
        var property = $('#property').val()

        if(property == "Proofpoint")
            $("#return_media_to_customer").val("No")
        else
            $("#return_media_to_customer").val("Yes")
    });

    $("form").on('submit', function() {
        pp_asset_tag = "PS" + $('#pp_asset_tag').val();
        manufacturer = $('#manufacturer').val();
        serial_number = $('#serial_number').val();
        property = $('#property').val();
        customer_name = $('#customer_name').val();
        cts = $('#cts').val();
        jira = $("#jira").val();
        label = $('#label').val();
        drive_location = $('#drive_location').val();
        drive_state = $('#drive_state').val();
        usb = $('#usb').val();
        power = $('#power').val();
        received_date = $('#received_date').val();
        shipping_carrier = $('#shipping_carrier').val();
        shipping_tracking_number = $('#shipping_tracking_number').val();
        sent_date = $('#sent_date').val();
        return_media_to_customer = $('#return_media_to_customer').val();
        notes = $('#notes').val();
        username = $('#username').val();
        essential = $('#essential').val();

        if(pp_asset_tag == null || pp_asset_tag == "") {
            alert("Please enter PP Asset Tag");
            $('#pp_asset_tag').focus();
            return;
        }

        $('#spinner').show();

        $.post("createDrive",
            {
                pp_asset_tag : pp_asset_tag,
                manufacturer : manufacturer,
                serial_number : serial_number,
                property : property,
                customer_name : customer_name,
                cts : cts,
                jira : jira,
                label : label,
                drive_location : drive_location,
                drive_state : drive_state,
                usb : usb,
                power : power,
                received_date : received_date,
                shipping_carrier : shipping_carrier,
                shipping_tracking_number : shipping_tracking_number,
                sent_date : sent_date,
                return_media_to_customer : return_media_to_customer,
                notes : notes,
                updated_by : username,
                is_update : is_update,
                essential: essential
            },
            function(data){
                $('#spinner').hide();
                $('#createDrive').hide();
                $('#result').empty();

                if(data.pp_asset_tag == undefined) {
                    var result = '<p><h3>Error:</h3>'
                        + '<br>' + data.message
                        + '</p>';
                }
                else {
                    var result = "<p><h3>Drive created in system with following details:</h3>"
                        + "<br>PP Asset Tag: " + data.pp_asset_tag
                        + "<br>Customer Name: " + data.customer_name
                        + "<br>CTS #: " + data.cts
                        + "<br>JIRA #: " + data.jira
                        + "<br>Manufacturer/Model: " + data.manufacturer
                        + "<br>Serial Number: " + data.serial_number
                        + "<br>Label: " + data.label
                        + "<br>USB: " + data.usb
                        + "<br>Power: " + data.power
                        + "<br>Property Owner: " + data.property
                        + "<br>Drive Location: " + data.drive_location
                        + "<br>Drive Status: " + data.drive_state
                        + "<br>Received Date: " + data.received_date
                        + "<br>Return Media to Customer: " + data.return_media_to_customer
                        + "<br>Essential: " + data.essential;

                    result += "<br>Notes: " + data.notes
                        + "<br>Created: " + data.created
                        + "<br>Updated: " + data.last_updated
                        + "<br>Updater: " + data.updated_by
                        + "</p>";
                }

                $('#result').append(result);
            }, "json");

        //return false prevents form from reloading/refreshing/going to other page
        return false;
    });
});
