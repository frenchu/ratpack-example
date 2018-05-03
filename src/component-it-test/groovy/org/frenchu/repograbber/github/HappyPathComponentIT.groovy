package org.frenchu.repograbber.github

import org.springframework.boot.test.autoconfigure.json.JsonTest

import ratpack.http.Status
import spock.lang.Unroll

@Unroll
@JsonTest
class HappyPathComponentIT extends ServerStubTestSpecification implements RatpackTestTrait {
    
    def 'should return correct #owner/#repository repository description from GitHub for happy path'() {        
        when:
        get "repositories/$owner/$repository"
        
        then:
        'assert response status and media type' Status.OK
        'assert successful response'(owner, repository, description, stars, createdAt)
    
        where:
        owner     | repository                  || description                                                      | stars | createdAt
        'frenchu' | 'hello-world'               || 'Hello world example'                                            | 5     | '2015-04-25T17:36:36Z'
        'frenchu' | 'eip-frameworks-comparison' || 'EIP frameworks comparison: Spring Integration vs. Apache Camel' | 15    | '2016-11-23T16:09:24Z'
    }
}
