package org.frenchu.repograbber.github

import org.frenchu.repograbber.Application

import io.netty.channel.ConnectTimeoutException
import ratpack.exec.Promise
import ratpack.http.Status
import ratpack.http.client.HttpClient
import ratpack.impose.ImpositionsSpec
import ratpack.impose.UserRegistryImposition
import ratpack.registry.Registry
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.ServerBackedApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ConnectionTimeoutComponentIT extends Specification {
    
    def static final HOST = 'http://localhost:8080'
    
    final HttpClient mockHttpClient = Mock()
    
    @AutoCleanup
    ServerBackedApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(Application) {
        @Override
        protected void addImpositions(final ImpositionsSpec impositions) {
            impositions.add(UserRegistryImposition.of(Registry.single(HttpClient, mockHttpClient)))
        }
    }
    
    @Delegate
    TestHttpClient client = testHttpClient(applicationUnderTest)


    def 'should respond with #statusCode on #error error'() {
        given:
        def uri = URI.create("$HOST/repos/$owner/$repository")
        
        when:
        get "repositories/$owner/$repository"
        
        then:
        1 * mockHttpClient.get(uri, _) >> Promise.sync { 
            throw new ConnectTimeoutException("Connect timeout connecting to $uri")
        }

        and:
        'assert internal server error response'(statusCode, error)
            
        where:
        owner     | repository    || statusCode | error
        'frenchu' | 'hello-world' || 500        | 'Connect timeout'
    }

    void 'assert internal server error response'(def statusCode, def error) {
        assert response.status == Status.of(statusCode)
        assert response.body.text.contains(error)
    }
}