package org.frenchu.repograbber.github

import org.junit.Rule

import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule

import spock.lang.Specification

abstract class ServerStubTestSpecification extends Specification {
    
    def static final WIREMOCK_ROOT_PATH = 'wiremock'
    
    @Rule
    WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().usingFilesUnderClasspath(WIREMOCK_ROOT_PATH))
    
    def stopServer() {
        wireMockRule.stop()
    }
}