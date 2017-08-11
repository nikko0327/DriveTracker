// created by zgraham on 8/7/17

/*
 * if you are new to OOP in Javascript please read this page: http://javascript.crockford.com/private.html
 * This class creates alert boxes, one object of Alert is an area where the alert will show up
 * constructor takes in a jQuery object, jQuerySelector, which is a jQuery selector object
 */

//Note: this constructor has quite a bit of logic/methods in it, but it is mostly okay because the object should
//      only be loaded once or twice per DOM

function Alert(jQuerySelector) {
    this.jQuerySelector = jQuerySelector;
    this.timeout = undefined;

    var that = this; // needed for the private functions
    // PRIVATE METHODS - only accessible inside the constructor, must be called through privileged methods//
    function privateResetAlertBox() {
        that.jQuerySelector.hide();
        that.jQuerySelector.removeAttr("style");
        that.jQuerySelector.empty();
        that.jQuerySelector.attr("class", "alert");
        that.jQuerySelector.append("<button type='button' class='close' aria-label='Close'>" +
            "<span class='close-alert' style='padding-right: 10px;' aria-hidden='true'>&times;</span>" + "</button>");
    }

    function privateFadeInSeconds(seconds) {
        that.timeout = window.setTimeout(function() {
            if (that.jQuerySelector !== undefined) {
                that.jQuerySelector.fadeTo(500, 0).slideUp(500, function () {
                    that.resetAlertBox(); // reset the alert box when the time is up
                });
            }
        }, seconds*1000);
    }

    function privateStartListening() {
        $(".close-alert").click(function() {
            clearTimeout(that.timeout); // first stop the timeout from continuing as all it does its hide/empty
            privateResetAlertBox(); // then reset the alert box
        });
    }

    // PRIVILEGED METHOD(S) - (privileged functions are used to access private methods from public methods) //
    // hide, empty, and un-stylize the alert box
    this.resetAlertBox = function() {
        privateResetAlertBox();
    };

    // fades the alert box away and takes in how many seconds it takes before it starts to fade
    this.fadeInSeconds = function(seconds) {
        privateFadeInSeconds(seconds);
    };

    // set up event listener
    this.startListening = function() {
        privateStartListening();
    }
}

// PUBLIC METHODS //
// display a message in the alert box (and the alert box itself) to the user
Alert.prototype.displayMessage = function(msg, msgClass, msgFirstWord, fadeTime) {
    this.resetAlertBox();
    this.jQuerySelector.show();
    this.jQuerySelector.addClass(msgClass);
    this.jQuerySelector.append(msgFirstWord + msg);
    clearTimeout(this.timeout);
    this.fadeInSeconds(fadeTime);
    this.startListening();
};

// clear the message and the alert box
Alert.prototype.clearCurrentMessage = function() {
    (this.jQuerySelector).attr("class", "alert");
    (this.jQuerySelector).empty();
    (this.jQuerySelector).hide();
};