// created by zgraham on 8/7/17

/*
 * This class creates alert boxes, one object of Alert is an area where the alert will show up
 * constructor takes in a jQuery object, jQuerySelector, which is a jQuery selector object
 */
function Alert(jQuerySelector) {
    this.jQuerySelector = jQuerySelector;
    this.jQuerySelector.hide(); // hide the alert until method is called on it
    this.jQuerySelector.attr("class", "alert"); // reset class to just alert
    this.jQuerySelector.empty(); // remove anything inside the alert
}

// display a green success message for user
Alert.prototype.displaySuccessMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-success");
    (this.jQuerySelector).append("<strong>Success! </strong>" + message);
};

// display a yellow warning message for user
Alert.prototype.displayWarningMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-warning");
    (this.jQuerySelector).append("<strong>Warning! </strong>" + message);
};

// display a red failure message for user
Alert.prototype.displayFailureMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-danger");
    (this.jQuerySelector).append("<strong>Oops! </strong>" + message);
};

// display a blue info/notice message for user
Alert.prototype.displayInfoMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-info");
    (this.jQuerySelector).append("<strong>Notice: </strong>" + message);
};

// clear the message and the alert box
Alert.prototype.clearCurrentMessage = function() {
    (this.jQuerySelector).attr("class", "alert");
    (this.jQuerySelector).empty();
    (this.jQuerySelector).hide();
};

// forces the user to scroll to where the box is created
Alert.prototype.focusOnMessage = function() {
    (this.jQuerySelector).get(0).scrollIntoView();
};