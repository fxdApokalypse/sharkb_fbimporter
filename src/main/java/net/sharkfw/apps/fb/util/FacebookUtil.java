package net.sharkfw.apps.fb.util;

/**
 * Convenient methods for facebook related tasks.
 */
public class FacebookUtil {

    /**
     * Creates url to a facebook user's profile out of its user id.
     *
     * @param userId the user id of the the user.
     *
     * @return the url to the profile of the user with the specified user id.
     */
    static public String createUserLink(String userId) {

        return " http://www.facebook.com/" + userId;
    }

}
