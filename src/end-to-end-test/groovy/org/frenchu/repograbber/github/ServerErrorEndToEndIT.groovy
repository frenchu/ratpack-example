package org.frenchu.repograbber.github

import ratpack.http.Status
import spock.lang.Unroll

import static org.frenchu.repograbber.github.Configuration.INCORRECT_HOST

@Unroll
class ServerErrorEndToEndIT extends AbstractEndToEndIT {

    def 'should reply with #statusCode server error when accessing #url with #configuration.value configuration'() {
        given:
        'connect to GitHub using' configuration
        
        when:
        get url
        
        then:
        response.status == Status.of(statusCode)
    
        where:
        url                                | configuration  || statusCode
        'repositories/frenchu/hello-world' | INCORRECT_HOST || 500
    }
}
