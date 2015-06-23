package net.sharkfw.apps.fb.core.service;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

/**
 * Provides access to the Facebook service by authentication via a user access token.
 */
public class FacebookAccessTokenServiceProvider implements FacebookServiceProvider {

    private final String appNamespace;
    private final String accessToken;

    public FacebookAccessTokenServiceProvider(String accessToken, String appNamespace) {
        this.accessToken = accessToken;
        this.appNamespace = appNamespace;
    }

    @Override
    public Facebook getApi() {
        return new FacebookTemplate(accessToken, appNamespace);
    }

    @Override
    public String getAppNamespace() {
        return this.appNamespace;
    }
}
