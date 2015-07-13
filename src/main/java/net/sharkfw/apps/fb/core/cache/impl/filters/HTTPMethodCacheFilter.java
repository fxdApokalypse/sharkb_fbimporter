package net.sharkfw.apps.fb.core.cache.impl.filters;

import net.sharkfw.apps.fb.core.cache.HttpRequestCache;
import net.sharkfw.apps.fb.core.cache.HttpRequestCacheFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;


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
