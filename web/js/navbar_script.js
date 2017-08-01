
$(document).ready(function() {
    var notification_button = $('#notification_button');
    var user = new User();

    console.log(user.getGroup());
    $('#group_name').text(user.getGroup() + " " + (user.isAdmin() ? "Admin" : "User"));

    // initialize the bootstrap toggle button
    notification_button.bootstrapToggle();

    //set it to off or on according to user preferences
    if (user.hasNotifyOn()) {
        notification_button.bootstrapToggle("on");
    }
    else {
        notification_button.bootstrapToggle("off");
    }


    // toggle the notification button when it is clicked
    $('.dropdown-menu input, .dropdown-menu label, .dropdown-menu span').click(function(e) {
        notification_button.bootstrapToggle('toggle');
        user.toggleNotifications();
        e.stopPropagation();
    });
});