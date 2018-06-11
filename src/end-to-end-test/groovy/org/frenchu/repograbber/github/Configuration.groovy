package org.frenchu.repograbber.github

enum Configuration {
    CORRECT("correct"),
    INCORRECT_CREDENTIALS("badcredentials"),
    INCORRECT_HOST("badhost")
    
    String value

    Configuration(String value) {
        this.value = value
    }
    
    String getValue() {
        return value
    }
}
