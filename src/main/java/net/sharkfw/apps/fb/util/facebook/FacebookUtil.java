package net.sharkfw.apps.fb.util.facebook;

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

    /**
     * Creates a url which acts as si to a PeerSNSemanticTag which
     * describes all friends of a facebook user with the specified userId.
     *
     * @param userId the user id of the the user.
     * @return the si to the friends super tag.
     */
    static public String createFriendsSI(String userId) {
        return createUserLink(userId) + "/friends";
    }

}
