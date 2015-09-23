package net.sharkfw.apps.fb.core.service;

import net.sharkfw.apps.fb.FacebookLogin;
import net.sharkfw.apps.fb.FacebookLoginException;
import net.sharkfw.apps.fb.core.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

/**
 * Provides access to the Facebook service by authentication via a user access token.
 */
public class FacebookAccessTokenServiceProvider implements FacebookServiceProvider {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookAccessTokenServiceProvider.class);

    private final String appNamespace;
    private final String accessToken;
    private FacebookTemplate api;

    public FacebookAccessTokenServiceProvider(String accessToken, String appNamespace) {
        this.accessToken = accessToken;
        this.appNamespace = appNamespace;
        createFBTemplate(accessToken);
    }

    private void createFBTemplate(String accessToken) {
        api = new FacebookTemplate(accessToken, this.appNamespace);
        CacheManager.makeCacheable(api);
    }

    @Override
    public FacebookTemplate getApi() {
        return api;
    }

    @Override
    public void update(String accessToken) {
        createFBTemplate(accessToken);
    }


    @Override
    public String getAppNamespace() {
        return this.appNamespace;
    }
}
