//simple 'class' called UserInfo
//allows centralized way to find out info about the user
//cookie with username is set at login
//User object can be created when needed
function User() {

    // get the username (found in cookies)
    this.username = function () {
        var nameEQ = "username=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    };

    //check which group user is in
}

//return true if user is an admin
User.prototype.isAdmin = function() {
    var result = false;
    $.ajax({
        type: 'POST',
        url: 'Admin',
        async: false, //important to have async set to false so that we wait for the server to give us info before returning
        data: {username: this.username},
        dataType: "json",
        success: function(data) {
            result = (data.result === "Yes");
        }
    });
    return result;
};

User.prototype.hasNotifyOn = function() {
    var result = false;
    $.ajax({
       type: 'POST',
       url: 'Notifications',
       async: false,
       data: {username: this.username, toggle: "No"},
       dataType: "json",
       success: function(data) {
           result = (data.result === "Yes");
       }
    });
    return result;
};

User.prototype.toggleNotifications = function() {
    $.ajax({
        type: 'POST',
        url: 'Notifications',
        async: false,
        data: {username: this.username, toggle: "Yes"},
        dataType: "json",
        success: function() {return;}
    });
};

//return name of group
User.prototype.getGroup = function() {
    var result = "Unknown";
    $.ajax({
        type: 'POST',
        url: 'Group',
        async: false,
        data: {username: this.username},
        dataType: "json",
        success: function(data) {
            if (data.result === "Yes") {
                result = data.group_name;
            }
            else result = data.result;
        }
    });
    return result;
};

User.prototype.getUsername = function() {
    return this.username;
};