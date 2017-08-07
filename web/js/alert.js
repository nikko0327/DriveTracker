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

Alert.prototype.displaySuccessMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-success");
    (this.jQuerySelector).append("<strong>Success! </strong>" + message);
};

Alert.prototype.displayWarningMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-warning");
    (this.jQuerySelector).append("<strong>Warning! </strong>" + message);
};

Alert.prototype.displayFailureMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-danger");
    (this.jQuerySelector).append("<strong>Oops! </strong>" + message);
};

Alert.prototype.displayInfoMessage = function(message) {
    (this.jQuerySelector).show();
    (this.jQuerySelector).addClass("alert-info");
    (this.jQuerySelector).append("<strong>Notice: </strong>" + message);
};

Alert.prototype.clearCurrentMessage = function() {
    (this.jQuerySelector).attr("class", "alert");
    (this.jQuerySelector).empty();
    (this.jQuerySelector).hide();
};

Alert.prototype.focusOnMessage = function() {
    (this.jQuerySelector).get(0).scrollIntoView();
};