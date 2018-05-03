package org.frenchu.repograbber.github

import java.time.Instant

import org.frenchu.repograbber.ResponseTransformer
import org.frenchu.repograbber.dto.RepositoryDetailsRS

import spock.lang.Specification

class GithubToGrabberResponseTransformerTest extends Specification {

    final ResponseTransformer<GithubResponse> transformer = new GithubToGrabberResponseTransformer()
    
    def 'should transform GitHub response to Repograbber response'() {
        given:
        def fullName = 'frenchu/hello-world'
        def description = 'Hello world example'
        def cloneUrl = 'https://github.com/frenchu/hello-world.git'
        def stargazersCount = 5
        def createdAt = Instant.ofEpochSecond(1429983396000L)
        def originalResponse = new GithubResponse(
                fullName, description, cloneUrl, stargazersCount, createdAt)
        
        when:
        RepositoryDetailsRS result = transformer.transform(originalResponse)
        
        then:
        result.fullName == fullName
        result.description == description
        result.cloneUrl == cloneUrl
        result.stars == stargazersCount
        result.createdAt == createdAt
    }
}
