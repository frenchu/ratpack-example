package org.frenchu.repograbber;

import org.frenchu.repograbber.dto.RepositoryDetailsRS;

/**
 * Abstraction for the transformation to the application specific response.
 *
 * @author Paweł Weselak
 *
 * @param <T> source class to transform from
 */
@FunctionalInterface
public interface ResponseTransformer<T> {

    /**
     * Transforms given object to application specific response.
     *
     * @param originalResponse original response to transform
     * @return application specific response data object
     */
    RepositoryDetailsRS transform(T originalResponse);
}
