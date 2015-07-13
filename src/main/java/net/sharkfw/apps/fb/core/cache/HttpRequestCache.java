package net.sharkfw.apps.fb.core.cache;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

/**
 * This caches should reuse HTTPResponseObjects in
 * order to avoid multiple http request to the
 * same HTTPResponses.
 */
public interface HttpRequestCache {

	/**
	 * Checks if a response to a corresponding response is already in the
	 * caches.
	 *
	 * @param request the corresponding request.
	 * @return true if the resonse is already in the caches.
	 */
	boolean hasResponse(HttpRequest request);

	/**
	 * Caches a HTTP Response with its corresponding request.
	 * If a response is already cached the implementation should
	 * update the caches entry for this response.
	 *
	 * @param request
	 * @param response
	 */
	void cache(HttpRequest request, ClientHttpResponse response);

	/**
	 * Retrieves the cached response for a specified request if
	 * it's already in the caches.
	 *
	 * @param request the specified request.
	 * @return the cached response or null if the response isn't in the caches.
	 */
	ClientHttpResponse get(HttpRequest request);

	/**
	 * Forces all cached Responses to become
	 * invalid in order to force to fetch a Response Object
	 * at the next request again.
	 */
	void invalidate();

}
