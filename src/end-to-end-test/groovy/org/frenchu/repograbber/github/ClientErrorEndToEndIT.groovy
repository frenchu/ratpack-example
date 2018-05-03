package org.frenchu.repograbber.github

import static org.frenchu.repograbber.github.Configuration.CORRECT
import static org.frenchu.repograbber.github.Configuration.INCORRECT_CREDENTIALS

import ratpack.http.Status
import spock.lang.Unroll

@Unroll
class ClientErrorEndToEndIT extends AbstractEndToEndIT {
    
    def 'should forward #statusCode status code and #message message from GitHub on client error while accessing #owner/#repository'() {
        given:
        'connect to GitHub using' configuration
        
        when:
        get "repositories/$owner/$repository"
        
        then:
        'assert response status and media type' Status.of(statusCode)
        'assert error response' message
    
        where:
        owner            | repository        | configuration         || statusCode | message
        'frenchu'        | 'hello-world'     | INCORRECT_CREDENTIALS || 401        | 'Bad credentials'
        'frenchu'        | 'no-repository'   | CORRECT               || 404        | 'Not Found'
        'pkiebasinski'   | 'jlabs-blog-2017' | CORRECT               || 404        | 'Not Found'
    }

    def 'should reply with #statusCode client error when trying to get #url resource'() {
        given:
        'connect to GitHub using' configuration

        when:
        get url

        then:
        response.status == Status.of(statusCode)

        where:
        url             | configuration || statusCode
        'repositories/' | CORRECT       || 404
    }
}
