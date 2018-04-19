$(document).ready(function () {

    var property = {
        "Customer": "Customer",
        "Proofpoint": "Proofpoint"
    };

    $.each(property, function (val, text) {
        $("select[id*=property]").append(
            $('<option></option>').val(val).html(text)
        );
    });
});
