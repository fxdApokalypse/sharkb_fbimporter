package net.sharkfw.apps.fb.core.cache;

import org.springframework.http.HttpRequest;

/**
 * This filter describes which request should be cached
 * by the @{link CachedRequestInterceptor}.
 */
public interface HttpRequestCacheFilter {

	/**
	 * Returns true if the specified request should be cached by the CachedRequestInterceptor.
	 * @param request the specified request
	 * @return true if the response for the specified request should be cached.
	 */
	boolean shouldBeCached(HttpRequest request);
}
