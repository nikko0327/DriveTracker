$(document).ready(function() {

    var shipping_types = {
        ""  : "",
        "FedEx" : "FedEx",
        "USPS" : "USPS",
        "UPS" : "UPS"
    };

    $.each(shipping_types, function(val, text) {
        $("select[id*=shipping_carrier]").append(
            $('<option></option>').val(val).html(text)
        );
    });
});