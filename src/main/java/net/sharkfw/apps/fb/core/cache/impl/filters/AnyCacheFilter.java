package net.sharkfw.apps.fb.core.cache.impl.filters;

import net.sharkfw.apps.fb.core.cache.HttpRequestCacheFilter;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AnyCacheFilter implements HttpRequestCacheFilter {
	@Override
	public boolean shouldBeCached(HttpRequest request) {
		return true;
	}
}
