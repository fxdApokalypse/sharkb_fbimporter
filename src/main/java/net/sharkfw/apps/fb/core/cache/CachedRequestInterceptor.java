package net.sharkfw.apps.fb.core.cache;

import net.sharkfw.apps.fb.core.cache.impl.filters.AnyCacheFilter;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


public class CachedRequestInterceptor implements ClientHttpRequestInterceptor {

	protected HttpRequestCache cache;
	protected HttpRequestCacheFilter responseCacheFilter;

	public CachedRequestInterceptor(HttpRequestCache cache) {
		this(cache, new AnyCacheFilter());
	}

	public CachedRequestInterceptor(HttpRequestCache cache, HttpRequestCacheFilter filter) {
		this.cache = cache;
		this.responseCacheFilter = filter;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

		boolean requestShouldBeCached = requestShouldBeCached(request);

		if (requestShouldBeCached && cache.hasResponse(request)) {
			return cache.get(request);
		}

		ClientHttpResponse response = execution.execute(request, body);

		if (requestShouldBeCached) {
			cache.cache(request, response);
		}
		return response;
	}

	private boolean requestShouldBeCached(final HttpRequest request) {
		return responseCacheFilter.shouldBeCached(request);
	}

}
