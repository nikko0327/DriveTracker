
$(document).ready(function() {

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

    $(".modal").on("show", function () {
        $("body").addClass("modal-open");
    }).on("hidden", function () {
        $("body").removeClass("modal-open")
    });
});
