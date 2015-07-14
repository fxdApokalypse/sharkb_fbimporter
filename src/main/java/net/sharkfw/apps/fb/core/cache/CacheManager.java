package net.sharkfw.apps.fb.core.cache;

import net.sharkfw.apps.fb.core.cache.impl.caches.InMemoryHttpResponseCache;
import net.sharkfw.apps.fb.core.cache.impl.filters.HttpMethodCacheFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class CacheManager {

	public static void makeCacheable(FacebookTemplate api) {
		RestTemplate rt = api.getRestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new CachedRequestInterceptor(new InMemoryHttpResponseCache(), new HttpMethodCacheFilter(HttpMethod.GET)));
		if (interceptors!= null) {
			interceptors.addAll(rt.getInterceptors());
		}
		rt.setInterceptors(interceptors);
	}

}
