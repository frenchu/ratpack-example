package org.frenchu.repograbber.github;

import java.time.Instant;

import org.frenchu.repograbber.ResponseTransformer;
import org.frenchu.repograbber.dto.RepositoryDetailsRS;

/**
 * Transforms Github API response to the application specific response.
 *
 * Transformation is done for the sake of decoupling Github API from the Repograbber API.
 *
 * @author Paweł Weselak
 *
 */
class GithubToGrabberResponseTransformer implements ResponseTransformer<GithubResponse> {

    @Override
    public RepositoryDetailsRS transform(final GithubResponse githubResponse) {
        String fullName = githubResponse.getFullName();
        String description =  githubResponse.getDescription();
        String cloneUrl = githubResponse.getCloneUrl();
        int starts = githubResponse.getStargazersCount();
        Instant createdAt = githubResponse.getCreatedAt();
        return new RepositoryDetailsRS(fullName, description, cloneUrl, starts, createdAt);
    }
}
