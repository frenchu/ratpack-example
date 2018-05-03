package org.frenchu.repograbber.github

import ratpack.http.Status
import spock.lang.Unroll

@Unroll
class GithubErrorComponentIT extends ServerStubTestSpecification implements RatpackTestTrait {
    
    def 'should forward #statusCode status code and message #message from GitHub on error'() {
        when:
        get "repositories/$owner/$repository"
        
        then:
        'assert response status and media type' Status.of(statusCode)
        'assert error response' message
    
        where:
        owner     | repository      || statusCode | message
        'frenchu' | 'hello-xxx'     || 401        | 'Bad credencials'
        'frenchu' | 'no-repository' || 404        | 'Not Found'
        'frenchu' | 'hello-kitty'   || 503        | 'Service unavailable'
    }
    
    def 'should respond with #statusCode status code when GitHub is down'() {
        given:
        stopServer()
        
        when:
        get "repositories/$owner/$repository"
        
        then:
        'assert internal server error response'(statusCode, error)
    
        where:
        owner     | repository    || statusCode | error
        'frenchu' | 'hello-world' || 500        | 'Connection refused'
    }
}
