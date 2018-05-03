package org.frenchu.repograbber.github

import java.time.Instant

import org.frenchu.repograbber.dto.RepositoryDetailsRS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

import ratpack.http.MediaType
import ratpack.http.Status
import spock.lang.Unroll

@Unroll
@JsonTest
class HappyPathEndToEndIT extends AbstractEndToEndIT {

    def 'should return correct #owner/#repository repository description from GitHub for happy path'() {
        given:
        'connect to GitHub using' Configuration.CORRECT
        
        when:
        get "repositories/$owner/$repository"
        
        then:
        'assert response status and media type' Status.OK
        'assert successful response'(owner, repository, description, stars, createdAt)

        where:
        owner     | repository                  || description                                                      | stars | createdAt
        'frenchu' | 'hello-world'               || 'Hello world example'                                            | 0     | '2015-04-25T17:36:36Z'
        'frenchu' | 'eip-frameworks-comparison' || 'EIP frameworks comparison: Spring Integration vs. Apache Camel' | 0     | '2016-11-23T16:09:24Z'
    }
}
