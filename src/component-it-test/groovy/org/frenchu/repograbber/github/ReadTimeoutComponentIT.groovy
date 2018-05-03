package org.frenchu.repograbber.github

import java.time.Duration

import org.frenchu.repograbber.Application

import ratpack.http.Status
import ratpack.http.client.HttpClient
import ratpack.impose.ImpositionsSpec
import ratpack.impose.UserRegistryImposition
import ratpack.registry.Registry
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.ServerBackedApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Unroll

@Unroll
class ReadTimeoutComponentIT extends ServerStubTestSpecification {

    def static final READ_TIMEOUT = 250

    @AutoCleanup
    ServerBackedApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(Application) {
        @Override
        protected void addImpositions(final ImpositionsSpec impositions) {
            impositions.add(UserRegistryImposition.of(Registry.single(HttpClient, HttpClient.of {
                spec -> spec.readTimeout(Duration.ofMillis(READ_TIMEOUT))
            } )))
        }
    }

    @Delegate
    TestHttpClient client = testHttpClient(applicationUnderTest)


    def 'should respond with #statusCode status code on #error'() {
        when:
        get "repositories/$owner/$repository"
        
        then:
        'assert internal server error response'(statusCode, error)
            
        where:
        owner     | repository || statusCode | error
        'frenchu' | 'delayed'  || 500        | 'HttpClientReadTimeout'
    }

    void 'assert internal server error response'(def statusCode, def error) {
        assert response.status == Status.of(statusCode)
        assert response.body.text.contains(error)
    }
}