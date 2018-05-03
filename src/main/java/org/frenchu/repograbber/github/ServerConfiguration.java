package org.frenchu.repograbber.github;

import org.frenchu.repograbber.ResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.registry.Registry;
import ratpack.spring.config.EnableRatpack;

/**
 * Represents Spring Boot configuration of the Ratpack HTTP server.
 *
 * Ratpack is a simple and lean library for creating fast, async and non-blocking HTTP apps.
 *
 * @see <a href="https://ratpack.io/manual/current/">Ratpack documentation</a>
 *
 * @author Paweł Weselak
 *
 */
@Configuration
@EnableRatpack
class ServerConfiguration {

    private final String apiUrl;
    private final String user;
    private final String token;

    ServerConfiguration(@Value("${org.frenchu.repograbber.github.url}") final String apiUrl,
                        @Value("${org.frenchu.repograbber.github.security.user}") final String user,
                        @Value("${org.frenchu.repograbber.github.security.token}") final String token) {
        this.apiUrl = apiUrl;
        this.user = user;
        this.token = token;
    }

    @Bean
    Action<Chain> repositories(final ObjectMapper jsonObjectMapper) {
        return chain -> chain.all(ctx -> ctx.next(Registry.single(ObjectMapper.class, jsonObjectMapper)))
                .get("repositories/:owner?/:repository?", githubRequestHandler());
    }

    @Bean
    Handler githubRequestHandler() {
        return new GithubRequestHandler(transformer(), apiUrl, user, token);
    }

    @Bean
    ResponseTransformer<GithubResponse> transformer() {
        return new GithubToGrabberResponseTransformer();
    }
}
