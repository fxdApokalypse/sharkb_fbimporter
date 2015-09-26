package net.sharkfw.apps.fb.core.cache.impl.caches;


import net.sharkfw.apps.fb.core.cache.HttpRequestCache;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
*  A straightforward response caches which stores the cached responses
 * in the memory. Because of that fact this caches is not well
 * suited for caching large sets of data.</p>
 *
 * <p>
 *     The term 'straightforward' is important because this caches don't implement
 *     any smart caching algorithms. It's only stores response objects
 *     in the memory and provides access to them. So this caches is not
 *     the fastest.
 * </p>
 */
// @Component("InMemoryCache")
public class InMemoryHttpResponseCache implements HttpRequestCache {

	protected Map<String, ClientHttpResponse> cachedEntries;

	/**
	 * Creates a empty InMemoryHTTPResponseCache
	 */
	public InMemoryHttpResponseCache() {
		cachedEntries = new HashMap<>();
	}

	@Override
	public boolean hasResponse(HttpRequest request) {
		return cachedEntries.containsKey(toKey(request));
	}

	@Override
	public void cache(HttpRequest request, ClientHttpResponse response) {
		cachedEntries.put(toKey(request), response);
	}

	@Override
	public ClientHttpResponse get(HttpRequest request) {
		return cachedEntries.get(toKey(request));
	}

	@Override
	public void invalidate() {
		cachedEntries.clear();
	}

	private String toKey(HttpRequest request) {
		return request.getURI().toASCIIString();
	}
}
