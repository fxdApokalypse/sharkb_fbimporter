package net.sharkfw.apps.fb.core.cache;

public interface CacheFactory  {
	HttpRequestCache getCache(String cacheType);
}
