package org.frenchu.repograbber.github;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

import java.net.URI;
import java.net.URISyntaxException;

import org.frenchu.repograbber.ResponseTransformer;
import org.frenchu.repograbber.dto.RepositoryDetailsRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Status;
import ratpack.http.client.HttpClient;
import ratpack.http.client.ReceivedResponse;

/**
 * Encapsulates GitHub request handling logic.
 *
 * @author Paweł Weselak
 *
 */
class GithubRequestHandler implements Handler {

    private static final Logger log = LoggerFactory.getLogger(GithubRequestHandler.class);

    private final ResponseTransformer<GithubResponse> transformer;
    private final String apiUrl;
    private final String user;
    private final String token;

    GithubRequestHandler(final ResponseTransformer<GithubResponse> transformer, final String apiUrl,
            final String user, final String token) {
        this.transformer = transformer;
        this.apiUrl = apiUrl;
        this.user = user;
        this.token = token;
    }

    @Override
    public void handle(final Context ctx) throws Exception {
        handleRequest(ctx);
    }

    private void handleRequest(final Context ctx) throws URISyntaxException {
        HttpClient httpClient = ctx.get(HttpClient.class);
        URI uri = buildUri(ctx);
        log.debug("Sending GET request to GitHub: {}", uri);
        httpClient.get(uri, requestSpec ->
                       requestSpec.basicAuth(user, token)
                                  .headers(headers -> headers.add("Accept", "application/vnd.github.v3+json")
                                                             .add("User-Agent", "RatPack")))
            .route(this::successfulResponse, receivedResponse -> handleSuccessfulResponse(receivedResponse, ctx))
            .then(receivedResponse -> handleErrorResponse(receivedResponse, ctx));
    }

    private URI buildUri(final Context ctx) throws URISyntaxException {
        String owner = ctx.getPathTokens().get("owner");
        String repository = ctx.getPathTokens().get("repository");
        return new URI(String.format("%s/repos/%s/%s", apiUrl, owner, repository));
    }

    private boolean successfulResponse(final ReceivedResponse receivedResponse) {
        return Status.OK.equals(receivedResponse.getStatus());
    }

    private void handleSuccessfulResponse(final ReceivedResponse receivedResponse, final Context ctx)
            throws Exception {
        log.debug("Received successful response from GitHub: {}", receivedResponse.getBody().getText());
        GithubResponse githubResponse = parseFromJson(receivedResponse, ctx);
        RepositoryDetailsRS repograbberResponse = transformer.transform(githubResponse);
        ctx.render(json(repograbberResponse));
    }

    private GithubResponse parseFromJson(final ReceivedResponse receivedResponse, final Context ctx)
            throws Exception {
        return ctx.parse(receivedResponse.getBody(), fromJson(GithubResponse.class));
    }

    private void handleErrorResponse(final ReceivedResponse receivedResponse, final Context ctx) {
        log.debug("Recivied error {} response from GitHub: {}", receivedResponse.getStatusCode(),
                receivedResponse.getBody().getText());
        receivedResponse.forwardTo(ctx.getResponse());
    }
}
