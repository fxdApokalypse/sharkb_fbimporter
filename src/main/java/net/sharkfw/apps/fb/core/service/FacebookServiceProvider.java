package net.sharkfw.apps.fb.core.service;


import org.springframework.social.ServiceProvider;
import org.springframework.social.facebook.api.Facebook;

/**
 * A Facebook service provider provide access to the facebook api.
 */
public interface FacebookServiceProvider extends ServiceProvider<Facebook> {
    /**
     * Return a instance of the Facebook api binding.
     *
     * @return the instance of the facebook api
     */
    Facebook getApi();

    /**
     * The name of the current App which should
     * used the Facebook Api binding.
     *
     * @return the name of the app.
     */
    String getAppNamespace();
}
