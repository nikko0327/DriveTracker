// Created by zgraham 08/1/17

/* simple 'class' called UserInfo
 * allows centralized way to find out info about the user
 * cookie with username is set at login
 * User object can be created when needed
 */
function User() {

    /*
     * about: private function that returns the username by
     *        looking at user's cookies
     * reminder: this.username returns the function,
     *           this.username() executes the function
     */
    this.username = function () {
        var nameEQ = "username=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    };

    /*
     * about: private function that most public functions use in order to
     *        post through AJAX to UserInfoServlet
     * why callbacks:
     *        AJAX is asynchronous meaning that it will run in a
     *        separate thread, so we should wait for it before
     *        continuing, so we use the callback method
     */
    this.postToUserInfoServlet = function (action, myCallback) {
        $.ajax({
            type: 'POST',
            url: 'UserInfoServlet',
            data: {action: action, username: this.username},
            dataType: 'json',
            success: function (data) {
                myCallback(data);
            }
        });
    }
}

/*
 * about: finds out if user is an admin
 * param: myCallback is a function that will be passed
 *        into isAdmin function, callback is called to
 *        send info to original function
 */
User.prototype.isAdmin = function (myCallback) {
    this.postToUserInfoServlet("isAdmin", function (data) {
        //return true if result is "Yes", else return false
        myCallback(data.result === "Yes");
    });
};

/*
 * about: finds out if user has notifications on
 * param: myCallback is a function that will be passed
 *        into hasNotifyOn function, callback is called to
 *        send info to original function
 */
User.prototype.hasNotifyOn = function (myCallback) {
    this.postToUserInfoServlet("notifications", function (data) {
        // return true if result is "Yes"
        myCallback(data.result === "Yes");
    });
};

/*
 * about: toggles the user's notifications
 * param: none
 */
User.prototype.toggleNotifications = function () {

    this.postToUserInfoServlet("toggleNotify", function (data) {
        if (data.result === "Error")
            alert("Error: " + data.result);
    });
};

/*
 * about: get the name of the user's group (i.e. Master, Essentials, or Enterprise)
 * param: myCallback is a function that will be passed
 *        into getGroup function, callback is called to
 *        send info to original function
 */
User.prototype.getGroup = function (myCallback) {

    this.postToUserInfoServlet("getGroup", function (data) {
        if (data.result === undefined || data.result === null) {
            myCallback("Unknown");
            alert("Error: Undefined or null value for group name!");
        }
        else if (data.result === "Error") {
            myCallback("Unknown");
            alert("Error: " + data.error);
        }
        else
            myCallback(data.result);
    });
};

User.prototype.getUsername = function () {
    return this.username();
};