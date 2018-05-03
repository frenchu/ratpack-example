package org.frenchu.repograbber.github;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents repository details response from GitHub REST API v3.
 *
 * @author Paweł Weselak
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class GithubResponse {

    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final int stargazersCount;
    private final Instant createdAt;

    GithubResponse(@JsonProperty("full_name") final String fullName,
                   @JsonProperty("description") final String description,
                   @JsonProperty("clone_url") final String cloneUrl,
                   @JsonProperty("stargazers_count") final int stargazersCount,
                   @JsonProperty("created_at") final Instant createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stargazersCount = stargazersCount;
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof GithubResponse) {
            GithubResponse other = (GithubResponse) object;
            return Objects.equals(fullName, other.fullName)
                    && Objects.equals(description, other.description)
                    && Objects.equals(cloneUrl, other.cloneUrl)
                    && stargazersCount == other.stargazersCount
                    && Objects.equals(createdAt, other.createdAt);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, description, cloneUrl, stargazersCount, createdAt);
    }
}
