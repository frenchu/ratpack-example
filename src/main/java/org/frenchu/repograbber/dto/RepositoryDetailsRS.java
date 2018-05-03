package org.frenchu.repograbber.dto;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON application specific response as a part of API.
 *
 * @author Paweł Weselak
 *
 */
public final class RepositoryDetailsRS {

    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final int stars;
    private final Instant createdAt;

    public RepositoryDetailsRS(@JsonProperty("fullName") final String fullName,
                               @JsonProperty("description") final String description,
                               @JsonProperty("cloneUrl") final String cloneUrl,
                               @JsonProperty("stars") final int stars,
                               @JsonProperty("createdAt") final Instant createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stars = stars;
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

    public int getStars() {
        return stars;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof RepositoryDetailsRS) {
            RepositoryDetailsRS other = (RepositoryDetailsRS) object;
            return Objects.equals(fullName, other.fullName)
                    && Objects.equals(description, other.description)
                    && Objects.equals(cloneUrl, other.cloneUrl)
                    && stars == other.stars
                    && Objects.equals(createdAt, other.createdAt);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, description, cloneUrl, stars, createdAt);
    }
}
