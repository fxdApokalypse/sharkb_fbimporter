package net.sharkfw.apps.fb.core.service;


import org.springframework.social.ServiceProvider;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

/**
 * A Facebook service provider provide access to the facebook api.
 */
public interface FacebookServiceProvider extends ServiceProvider<Facebook> {
    /**
     * Return a instance of the Facebook api binding.
     *
     * @return the instance of the facebook api
     */
    FacebookTemplate getApi();

    /**
     * The name of the current App which should
     * used the Facebook Api binding.
     *
     * @return the name of the app.
     */
    String getAppNamespace();

    /**
     * Updates the underlying access token
     * in order to renew the access token.
     * @param accessToken
     */
    void update(String accessToken);
}
