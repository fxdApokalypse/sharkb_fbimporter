package net.sharkfw.apps.fb.core.cache.impl.filters;

import net.sharkfw.apps.fb.core.cache.HttpRequestCacheFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * Created by fxdapokalypse on 11.07.2015.
 */

@Component
public class HttpMethodCacheFilter implements HttpRequestCacheFilter {

	private HttpMethod httpMethod;

	public HttpMethodCacheFilter(HttpMethod method) {
		Assert.notNull(method);
		this.httpMethod = method;
	}

	@Override
	public boolean shouldBeCached(HttpRequest request) {
		return httpMethod.equals(request.getMethod());
	}
}
