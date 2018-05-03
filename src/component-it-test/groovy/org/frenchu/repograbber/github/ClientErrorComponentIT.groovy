package org.frenchu.repograbber.github

import ratpack.http.Status
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ClientErrorComponentIT extends Specification implements RatpackTestTrait {
    
    def 'should reply with #statusCode client error when trying to get #url resource'() {
        when:
        get url
        
        then:
        response.status == Status.of(statusCode)
    
        where:
        url                || statusCode
        'nonexistent/path' || 404
    }
    
    def 'should reply with #statusCode client error when trying to use POST method'() {
        when:
        post url
            
        then:
        response.status == Status.of(statusCode)
        
        where:
        url                                || statusCode
        'repositories/frenchu/hello-world' || 405
    }
}
