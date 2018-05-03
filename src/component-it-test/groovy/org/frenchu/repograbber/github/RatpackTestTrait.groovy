package org.frenchu.repograbber.github

import static ratpack.test.http.TestHttpClient.testHttpClient

import java.time.Instant

import org.frenchu.repograbber.Application
import org.frenchu.repograbber.dto.RepositoryDetailsRS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.json.JacksonTester

import com.jayway.jsonpath.JsonPath

import ratpack.http.MediaType
import ratpack.http.Status
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.ServerBackedApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup

trait RatpackTestTrait {

    @AutoCleanup
    ServerBackedApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(Application)
    
    @Delegate
    TestHttpClient client = testHttpClient(applicationUnderTest)

    @Autowired
    JacksonTester<RepositoryDetailsRS> json


    void 'assert successful response'(def owner, def repository, def description, def stars, def createdAt) {
        assert responseBody() == new RepositoryDetailsRS(
                "$owner/$repository",
                description,
                "https://github.com/${owner}/${repository}.git",
                stars,
                Instant.parse(createdAt))
        assert 'createdAt from response body'() == createdAt
    }

    void 'assert response status and media type'(def status) {
        assert response.status == status
        assert response.headers['Content-Type'].contains(MediaType.APPLICATION_JSON)
    }

    def responseBody() {
        json.parseObject response.body.text
    }

    def 'createdAt from response body'() {
        JsonPath.read(response.body.text, '$.createdAt')
    }

    void 'assert error response'(def message) {
        assert 'message from response body'() == message
    }

    def 'message from response body'() {
        JsonPath.read(response.body.text, '$.message')
    }

    void 'assert internal server error response'(def statusCode, def error) {
        assert response.status == Status.of(statusCode)
        assert response.body.text.contains(error)
    }
}