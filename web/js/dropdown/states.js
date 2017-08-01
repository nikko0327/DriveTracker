$(document).ready(function() {

    $('#shipping_info').hide();

    states = {
        "" : "",
        "Media Received from Customer" : "Media Received from Customer",
        "Media Returned from Customer" : "Media Returned from Customer",
        "Media Returned to Customer" : "Media returned to Customer",
        "Media Shipped to Customer" : "Media Shipped to Customer",
        "PS to Deliver Media to DC" : "PS to Deliver Media to DC",
        "PS to Pick Up Media from DC" : "PS to Pick Up Media from DC",
        "Media Waiting to be Connected" : "Media Waiting to be Connected",
        "Media Copying" : "Media Copying",
        "Media Needs to Be Disconnected from Host" : "Media Needs to be Disconnected from Host",
        "Media Connected to Host" : "Media Connected to Host",
        "Media Resides in 888/TEM to Update" : "Media Resides in 888/TEM to Update",
        "Media resides in TOR/TEM to Update" : "Media Resides in TOR/TEM to Update",
        "Media Shipped to Sunnyvale" : "Media Shipped to Sunnyvale",
        "Media Shipped to Toronto" : "Media Shipped to Toronto",
        "Media Encrypted and Wiped / Ready for New Project" : "Media Encrypted and Wiped / Ready for New Project",
        "Import Complete / Return Media to Customer" : "Import Complete / Return Media to Customer",
        "Wipe Media for New Project" : "Wipe Media for New Project",
        "Project Closed / Media Returned" : "Project Closed / Media Returned",
        "Export Project Drive" : "Export Project Drive",
        "Waiting for AWS Upload" : "Waiting for AWS Upload",
        "AWS Upload Complete" : "AWS Upload Complete",
        "Waiting Return from AWS" : "Waiting Return from AWS"
    };


    $.each(states, function(val, text) {
        $('select[id*=drive_state]').append(
            $('<option></option>').val(val).html(text)
        );
    });

    $('select[id*=state]').on('change', function() {
        showShippingInfo($('select[id*=state]').val());
    });
});

function showShippingInfo(state) {
    if(state == "Media Shipped to Customer"
        || state == "Import Complete / Return Media to Customer")
        $('#shipping_info').show();

    else
        $('#shipping_info').hide();
}
