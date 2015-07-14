package net.sharkfw.apps.fb.core.service;

import net.sharkfw.apps.fb.core.cache.CacheManager;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

/**
 * Provides access to the Facebook service by authentication via a user access token.
 */
public class FacebookAccessTokenServiceProvider implements FacebookServiceProvider {

    private final String appNamespace;
    private final String accessToken;
    private FacebookTemplate api;

    public FacebookAccessTokenServiceProvider(String accessToken, String appNamespace) {
        this.accessToken = accessToken;
        this.appNamespace = appNamespace;
        api = new FacebookTemplate(accessToken, appNamespace);
        CacheManager.makeCacheable(api);
    }

    @Override
    public FacebookTemplate getApi() {
        return api;
    }

    @Override
    public String getAppNamespace() {
        return this.appNamespace;
    }
}
