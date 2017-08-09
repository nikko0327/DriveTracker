// created by zgraham on 8/7/17

/*
 * This class creates alert boxes, one object of Alert is an area where the alert will show up
 * constructor takes in a jQuery object, jQuerySelector, which is a jQuery selector object
 */
function Alert(jQuerySelector) {

    this.successFadeTime = 3; //set the fade time of the success alerts to 3 seconds
    this.failureFadeTime = 5; //set fade time of failure alerts to 5 seconds (so they have longer to read it)

    this.jQuerySelector = jQuerySelector;
    this.jQuerySelector.hide(); // hide the alert until method is called on it
    this.jQuerySelector.empty(); // remove anything inside the alert
    this.jQuerySelector.attr("class", "alert"); // reset class to just alert
    // add the close (X) button
    this.jQuerySelector.append("<button type='button' class='close' aria-label='Close'>" +
        "<span class='close-alert' style='padding-right: 10px;' aria-hidden='true'>&times;</span>" + "</button>");
    var that = this;

    // private function that fades the alert box away
    this.fadeInSeconds = function(seconds) {
        window.setTimeout(function() {
            if (that.jQuerySelector !== undefined) {
                (that.jQuerySelector).fadeTo(500, 0).slideUp(500, function () {
                    that.jQuerySelector.removeAttr("style"); // get rid of styling caused by .fadeTo
                    that.jQuerySelector.empty(); // empty the alert box
                    that.jQuerySelector.hide(); // hide the alert box again
                });
            }
        }, seconds*1000);

    }
}

// display a green success message for user
Alert.prototype.displaySuccessMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-success");
    (this.jQuerySelector).append("<strong>Success! </strong>" + message);
    this.fadeInSeconds(this.successFadeTime);
};

// display a yellow warning message for user
Alert.prototype.displayWarningMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-warning");
    (this.jQuerySelector).append("<strong>Warning! </strong>" + message);
    this.fadeInSeconds(this.successFadeTime);
};

// display a red failure message for user
Alert.prototype.displayFailureMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-danger");
    (this.jQuerySelector).append("<strong>Oops! </strong>" + message);
    this.fadeInSeconds(this.failureFadeTime);
};

// display a blue info/notice message for user
Alert.prototype.displayInfoMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-info");
    (this.jQuerySelector).append("<strong>Notice: </strong>" + message);
    this.fadeInSeconds(this.failureFadeTime);
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