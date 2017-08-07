
$(document).ready(function() {

    var user  = new User();
    var username = user.getUsername();

    $('#result').empty();

    var create_alert_box = "<div id='create_alert_box'></div>";
    $('#result').append(create_alert_box);

    //add startsWith function to the String prototype
    if (typeof String.prototype.startsWith != 'function') {
        String.prototype.startsWith = function (str){
            return this.slice(0, str.length) == str;
        };
    }

    $('#pp_asset_tag').focus();
    $('#sent_date').datepicker({ dateFormat: "yy-mm-dd"});
    $('#received_date').datepicker({ dateFormat: "yy-mm-dd"}).datepicker('setDate', new Date());

    var essentials_alert = new Alert($('#essentials_alert_box'));
    essentials_alert.displayInfoMessage("Using Archiving/Enterprise Prefix Tag (PS)");
    $('#essential').on('change', function() {
        var PSorPSE = $('#PSorPSE');
        if ($(this).val() === "Yes") {
            essentials_alert.clearCurrentMessage();
            essentials_alert.displayInfoMessage("Using Essentials Prefix Tag (PSE)");
            PSorPSE.text("PSE");
        }
        else if ($(this).val() === "No") {
            essentials_alert.clearCurrentMessage();
            essentials_alert.displayInfoMessage("Using Archiving/Enterprise Prefix Tag (PS)");
            PSorPSE.text("PS");
        }
    });

    $(document).on('change', '#property', function() {
        var property = $('#property').val();

        if(property == "Proofpoint")
            $("#return_media_to_customer").val("No");
        else
            $("#return_media_to_customer").val("Yes");
    });

    $("form").on('submit', function() {
        var essential = $('#essential').val();

        if (essential === "Yes")
            var pp_asset_tag = "PSE" + $('#pp_asset_tag').val();
        else
            var pp_asset_tag = "PS" + $('#pp_asset_tag').val();

        var manufacturer = $('#manufacturer').val();
        var serial_number = $('#serial_number').val();
        var property = $('#property').val();
        var customer_name = $('#customer_name').val();
        var cts = $('#cts').val();
        var jira = $("#jira").val();
        var label = $('#label').val();
        var drive_location = $('#drive_location').val();
        var drive_state = $('#drive_state').val();
        var usb = $('#usb').val();
        var power = $('#power').val();
        var received_date = $('#received_date').val();
        var shipping_carrier = $('#shipping_carrier').val();
        var shipping_tracking_number = $('#shipping_tracking_number').val();
        var sent_date = $('#sent_date').val();
        var return_media_to_customer = $('#return_media_to_customer').val();
        var notes = $('#notes').val();

        if(pp_asset_tag == null || pp_asset_tag == "") {
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
                is_update : "false",
                essential: essential
            },
            function(data){
                $('#createDrive').hide();
                essentials_alert.clearCurrentMessage();
                var alert = new Alert($('#create_alert_box'));

                if(data.pp_asset_tag === undefined) {
                    alert.displayFailureMessage("Error: " + data.message);
                }
                else {
                    alert.displaySuccessMessage("Drive created successfully!");
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
                        + "<br>Essentials: " + data.essential
                        + "<br>Notes: " + data.notes
                        + "<br>Created: " + data.created
                        + "<br>Updated: " + data.last_updated
                        + "<br>Updater: " + data.updated_by
                        + "</p>";
                }

                $('#result').append(result);
                $('#spinner').hide();
            }, "json");

        //return false prevents form from reloading/refreshing/going to other page
        return false;
    });
});