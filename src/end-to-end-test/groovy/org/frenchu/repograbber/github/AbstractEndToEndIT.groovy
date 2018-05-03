package org.frenchu.repograbber.github

import spock.lang.Specification

abstract class AbstractEndToEndIT extends Specification implements RatpackTestTrait {
    
    def 'connect to GitHub using'(def configuration) {
        System.setProperty('spring.profiles.active', configuration.value)
    }
    
    def cleanup() {
        System.clearProperty('spring.profiles.active')
    }
}
