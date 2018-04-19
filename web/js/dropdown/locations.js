$(document).ready(function () {

    var locations = {
        "": "",
        "SC4: Inbound": "SC4: Inbound",
        "SC4: Outbound": "SC4: Outbound",
        "PS-892 Shipping/Receiving": "PS-892 Shipping/Receiving",
        "AWS": "AWS",
        "In Transit - AWS": "In Transit - AWS",
        "In Transit - BRA": "In Transit - BRA",
        "In Transit - FRA": "In Transit - FRA",
        "In Transit - customer": "In Transit - customer",
        "In Transit - Sunnyvale": "In Transit - Sunnyvale",
        "In Transit - Toronto": "In Transit - Toronto",
        "In Transit - Markham DC": "In Transit - Markham DC",
        "In Transit - SC4 DC": "In Transit - SC4 DC",
        "FRA DC": "FRA DC",
        "PS - Toronto - Locked Cabinet": "PS - Toronto - Locked Cabinet",
        "PS - Toronto - Locked Cabinet Upper": "PS - Toronto - Locked Cabinet Upper",
        "PS - Toronto - Locked Cabinet Lower": "PS - Toronto - Locked Cabinet Lower",
        "PS-888-Cab 1-Shelf 1": "PS-888-Cab 1-Shelf 1",
        "PS-888-Cab 1-Shelf 2": "PS-888-Cab 1-Shelf 2",
        "PS-888-Cab 1-Shelf 3": "PS-888-Cab 1-Shelf 3",
        "PS-888-Cab 1-Shelf 4": "PS-888-Cab 1-Shelf 4",
        "PS-888-Cab 1-Shelf 5": "PS-888-Cab 1-Shelf 5",
        "PS-888-Cab 2-Shelf 1": "PS-888-Cab 2-Shelf 1",
        "PS-888-Cab 2-Shelf 2": "PS-888-Cab 2-Shelf 2",
        "PS-888-Cab 2-Shelf 3": "PS-888-Cab 2-Shelf 3",
        "PS-888-Cab 2-Shelf 4": "PS-888-Cab 2-Shelf 4",
        "PS-888-Cab 2-Shelf 5": "PS-888-Cab 2-Shelf 5",
        "PS-888-Cab A-Drawer 1": "PS-888-Cab A-Drawer 1",
        "PS-888-Cab A-Drawer 2": "PS-888-Cab A-Drawer 2",
        "PS-888-Cab A-Drawer 3": "PS-888-Cab A-Drawer 3",
        "PS-888-Cab A-Drawer 4": "PS-888-Cab A-Drawer 4",
        "PS-888-Cab B-Drawer 1": "PS-888-Cab B-Drawer 1",
        "PS-888-Cab B-Drawer 2": "PS-888-Cab B-Drawer 2",
        "PS-888-Cab B-Drawer 3": "PS-888-Cab B-Drawer 3",
        "PS-888-Cab B-Drawer 4": "PS-888-Cab B-Drawer 4",
        "PS-888-Cab C-Drawer 1": "PS-888-Cab C-Drawer 1",
        "PS-888-Cab C-Drawer 2": "PS-888-Cab C-Drawer 2",
        "PS-888-Cab C-Drawer 3": "PS-888-Cab C-Drawer 3",
        "PS-888-Cab C-Drawer 4": "PS-888-Cab C-Drawer 4",
        "SC4 Host": "SC4 Host",
        "MARK Host": "MARK Host"
    };

    $.each(locations, function (val, text) {
        $("select[id*=drive_location]").append(
            $('<option></option>').val(val).html(text)
        );
    });
});
