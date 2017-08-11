function Enum() {
    this.MESSAGE = {
        SUCCESS: 1,
        INFO: 2,
        WARNING: 3,
        FAILURE: 4,
        props: {
            1: {name: "success", first_word: "<strong>Success! </strong>", class_code: "alert-success", ttl: 3},
            2: {name: "info", first_word: "<strong>Notice: </strong>", class_code: "alert-info", ttl: 3},
            3: {name: "warning", first_word: "<strong>Warning: </strong>", class_code: "alert-warning", ttl: 5},
            4: {name: "failure", first_word: "<strong>Oops! </strong>", class_code: "alert-danger", ttl: 5}
        }
    };
    Object.freeze(this.MESSAGE); // prevents me from accidentally changing values of MESSAGE
}
