
$(document).ready(function() {
    var notifyButton = $('#notification_button');
    var username = $('#user_name');
    var groupName = $('#group_name');
    var user = new User();

    username.text(user.getUsername() + "@proofpoint.com");

    //TODO: isAdmin should not have to rely on getGroup working,
    //      so I should separate the two functions at some point
    user.getGroup(function(nameOfGroup) {
        user.isAdmin(function(isAnAdmin) {
            groupName.text(nameOfGroup + " " + (isAnAdmin ? "Admin" : "User"));
        });
    });

    // initialize the bootstrap toggle button
    notifyButton.bootstrapToggle();

    //set it to off or on according to user preferences
    user.hasNotifyOn(function(notifyIsOn) {
        if(notifyIsOn)
            notifyButton.bootstrapToggle("on");
        else
            notifyButton.bootstrapToggle("off");
    });

    // toggle the notification button when it is clicked
    $('.dropdown-menu input, .dropdown-menu label, .dropdown-menu span').click(function(e) {
        notifyButton.bootstrapToggle('toggle');
        user.toggleNotifications();
        e.stopPropagation();
    });
});