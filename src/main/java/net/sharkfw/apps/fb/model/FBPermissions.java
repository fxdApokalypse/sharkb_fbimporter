package net.sharkfw.apps.fb.model;

import org.springframework.social.facebook.api.Permission;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FBPermissions {

    /**
     * <p>
     * Provides access to a subset of items that are part of a person's public profile. <br>
     * A person's public profile refers to the following properties on the user object by default: <br>
     * </p>
     * <ul>
     *     <li>id</li>
     *     <li>name</li>
     *     <li>first_name</li>
     *     <li>last_name</li>
     *     <li>age_rank</li>
     *     <li>link</li>
     *     <li>gender</li>
     *     <li>locale</li>
     *     <li>timezone</li>
     *     <li>updated_time</li>
     *     <li>verified</li>
     * </ul>
     *
     * <p>
     * On the web, public_profile is implied with every request and isn't required, although the best practice is to declare it. <br>
     * On iOS and Android, you must manually request it as part of your login flow. <br>
     * </p>
     *
     * <p>
     *     <code>gender</code> &amp; <code>locale</code> can only be accessed if:
     * </p>
     * <ul>
     *     <li>The person queried is the person using the app.</li>
     *     <li>The person queried is using the app, and is a friend of the person using the app.</li>
     *     <li>The person queried is using the app, is not a friend of the person using the app, but the app includes either an app access token or an <a href="/docs/graph-api/securing-requests#appsecret_proof"><code>appsecret_proof</code></a> argument with the call.</li>
     * </ul>
     *
     * <p><code>timezone</code> &amp; <code>verified</code> can only be accessed if:</p>
     * <ul>
     *     <li>The person queried is equal to the person making the request.</li>
     * </ul>
     *
     * <h4 id="review-public_profile">Review</h4>
     * <p>Your app may use this permission without review from Facebook.</p>
     *
     */
    public static final String PUBLIC_PROFILE = "public_profile";

    /**
     * <p>Provides access the list of friends that also use your app. These friends can be found on the <a href="/docs/graph-api/reference/user/friends">friends edge</a> on the user object.</p>
     * <p>In order for a person to show up in one person's friend list, <em>both</em> people must have decided to share their list of friends with your app and not disabled that permission during login.  Also both friends must have been asked for <code>user_friends</code> during the login process.</p>
     * <h4 id="review-public_profile">Review</h4>
     * <p>Your app may use this permission without review from Facebook.</p>
     */
    public static final String USER_FRIENDS = "user_friends";

    /**
     * <p>Provides access to the person's primary email address via the <code>email</code> property on the <a href="/docs/graph-api/reference/user"><code>user</code></a> object.</p>
     * <p>Do not spam users.  Your use of email must comply with both <a href="http://www.facebook.com/terms.php">Facebook policies</a> and with the <a href="http://l.facebook.com/l.php?u=http%3A%2F%2Fwww.ftc.gov%2Fbcp%2Fedu%2Fpubs%2Fbusiness%2Fecommerce%2Fbus61.shtm&amp;h=MAQFw03ca&amp;s=1" target="_blank" rel="nofollow" onmouseover="LinkshimAsyncLink.swap(this, &quot;http:\/\/www.ftc.gov\/bcp\/edu\/pubs\/business\/ecommerce\/bus61.shtm&quot;);" onclick="LinkshimAsyncLink.referrer_log(this, &quot;http:\/\/www.ftc.gov\/bcp\/edu\/pubs\/business\/ecommerce\/bus61.shtm&quot;, &quot;\/si\/ajax\/l\/render_linkshim_log\/?u=http\u00253A\u00252F\u00252Fwww.ftc.gov\u00252Fbcp\u00252Fedu\u00252Fpubs\u00252Fbusiness\u00252Fecommerce\u00252Fbus61.shtm&amp;h=MAQFw03ca&amp;render_verification=0&amp;enc&amp;d&quot;);">CAN-SPAM Act</a>.</p>
     * <p>Note, even if you request the <code>email</code> permission it is not guaranteed you will get an email address. For example, if someone signed up for Facebook with a phone number instead of an email address, the email field may be empty.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String EMAIL = "email";

    /**
     * <p>Provides access to a person's personal description (the 'About Me' section on their Profile) through the <code>bio</code> property on the <a href="https://developers.facebook.com/docs/graph-api/reference/user"><code>User</code> object</a>.</p>
     * <p><strong>This permission does not give access to a person's public profile data.</strong>  A person's name, profile picture, locale, age range and gender are included by default with the <a href="#reference-public_profile"><code>public_profile</code></a> permission.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ABOUT_ME = "user_about_me";

    /**
     * <p>Provides access to all common books actions published by any app the person has used. This includes books they've <a href="https://developers.facebook.com/docs/reference/opengraph/action-type/books.reads/">read</a>, <a href="/docs/reference/opengraph/action-type/books.wants_to_read/">want to read</a>, <a href="/docs/reference/opengraph/action-type/books.rates/">rated</a> or <a href="/docs/reference/opengraph/action-type/books.quotes/">quoted</a>.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ACTIONS_BOOKS = "user_actions.books";

    /**
     * <p>Provides access to all common Open Graph fitness actions published by any app the person has used. This includes <a href="https://developers.facebook.com/docs/reference/opengraph/action-type/fitness.runs/">runs</a>, <a href="/docs/reference/opengraph/action-type/fitness.walks/">walks</a> and <a href="/docs/reference/opengraph/action-type/fitness.bikes/">bikes</a> actions.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ACTIONS_FITNESS = "user_actions.fitness";

    /**
     * <p>Provides access to all common Open Graph music actions published by any app the person has used. This includes <a href="/docs/reference/opengraph/action-type/music.listens/">songs they've listened to</a>, and <a href="/docs/reference/opengraph/action-type/music.playlists/">playlists they've created</a>.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ACTIONS_MUSIC = "user_actions.music";

    /**
     * <p>Provides access to all common Open Graph news actions published by any app the person has used which publishes these actions. This includes <a href="/docs/reference/opengraph/action-type/news.reads/">news articles they've read</a> or <a href="/docs/reference/opengraph/action-type/news.publishes/">news articles they've published</a>.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ACTIONS_NEWS = "user_actions.news";

    /**
     * <p>Provides access to all common Open Graph video actions published by any app the person has used which publishes these actions. This includes <a href="/docs/reference/opengraph/action-type/news.reads/"></a><a href="/docs/opengraph/guides/video.watches">videos they've watched</a>, <a href="/docs/opengraph/guides/video.rates">videos they've rated</a> and <a href="/docs/opengraph/guides/video.wants_to_watch">videos they want to watch</a>.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ACTIONS_VIDEO = "user_actions.video";

    /**
     * <p>Provides access to all of the person's custom Open Graph actions in the given app.</p>
     * <h4 id="review-user_about_me">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_ACTIONS_APP = "user_actions:{app_namespace}";

    /**
     * <p>Access the date and month of a person's birthday. This may or may not include the person's year of birth, dependent upon their privacy settings and the access token being used to query this field.</p>
     * <p>Please note most integrations will only need <code>age_range</code> which comes as part of the <a href="#reference-public_profile"><code>public_profile</code></a> permission.</p>
     * <h4 id="review-user_birthday">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_BIRTHDAY = "user_birthday";

    /**
     * <p>Provides access to a person's education history through the <code>education</code> field on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_education_history">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_EDUCATION_HISTPORY = "user_education_history";

    /**
     * <p>Provides read-only access to the <a href="/docs/graph-api/reference/event">Events</a> a person is hosting or has RSVP'd to.</p>
     * <h4 id="review-user_events">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     *
     */
    public static final String USER_EVENTS = "user_events";

    /**
     * <p>Provides access to read a person's game activity (scores, achievements) in any game the person has played.</p>
     * <h4 id="review-user_games_activity">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_GAMES_ACTIVITY = "user_games_activity";

    /**
     * <p>Enables your app to read the <a href="/docs/graph-api/reference/group">Groups</a> a person is a member of through the <a href="/docs/graph-api/reference/user/groups">groups edge</a> on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <p>This permission does not allow you to create groups on behalf of a person.  It is not possible to create groups via the Graph API.</p>
     * <h4 id="review-user_groups">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_GROUPS = "user_groups";

    /**
     * <p>Provides access to a person's hometown location through the <code>hometown</code> field on the <a href="/docs/graph-api/reference/user">User object</a>. This is set by the user on the Profile.</p>
     * <h4 id="review-user_hometown">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_HOMETOWN = "user_hometown";

    /**
     * <p>Provides access to the list of all Facebook Pages and Open Graph objects that a person has liked. This list is available through the <a href="/docs/graph-api/reference/user/likes">likes edge</a> on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_likes">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_LIKES = "user_likes";

    /**
     * <p>Provides access to a person's current city through the <code>location</code> field on the <a href="/docs/graph-api/reference/user">User object</a>. The current city is set by a person on their Profile.</p>
     * <p>The current city is not necessarily the same as a person's hometown.</p>
     * <h4 id="review-user_location">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_LOCATION = "user_location";

    /**
     * <p>Enables your app to read the <a href="/docs/graph-api/reference/group">Groups</a> a person is an admin of through the <a href="/docs/graph-api/reference/user/groups">groups edge</a> on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <p>This permission does not allow you to create groups on behalf of a person. It is not possible to create groups via the Graph API. This does not let you read the groups a user is just a member of.</p>
     * <h4 id="review-user_managed_groups">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_MANAGED_GROUPS = "user_managed_groups";

    /**
     * <p>Provides access to the photos a person has uploaded or been tagged in. This is available through the <a href="/docs/graph-api/reference/user/photos">photos edge</a> on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_photos">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_PHOTOS = "user_photos";

    /**
     * <p>Provides access to the posts on a person's Timeline. Includes their own posts, posts they are tagged in, and posts other people make on their Timeline.</p>
     * <h4 id="review-user_posts">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     *
     */
    public static final String USER_POSTS = "user_posts";

    /**
     * <p>Provides access to a person's relationship status, significant other and family members as fields on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_relationships">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_RELATIONSHIPS = "user_relationships";

    /**
     * <p>Provides access to a person's relationship interests as the <code>interested_in</code> field on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_relationship_details">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_RELATIONSHIP_DETAILS = "user_relationship_details";

    /**
     * <p>Provides access to a person's religious and political affiliations.</p>
     * <h4 id="review-user_religion_politics">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_RELIGION_POLITICS = "user_religion_politics";

    /**
     * <p>Provides access to <a href="/docs/graph-api/reference/user/feed/">a person's statuses</a>. These are posts on Facebook which don't include <a href="/docs/graph-api/reference/link">links</a>, <a href="/docs/graph-api/reference/video">videos</a> or <a href="/docs/graph-api/reference/photo">photos</a>.</p>
     * <h4 id="review-user_status">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_STATUS = "user_status";

    /**
     * <p>Provides access to <a href="/docs/graph-api/reference/user/tagged_places">the Places a person has been tagged at</a> in photos, videos, statuses and links.</p>
     * <h4 id="review-user_tagged_places">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_TAGGED_PLACES = "user_tagged_places";

    /**
     * <p>Provides access to <a href="https://developers.facebook.com/docs/graph-api/reference/user/videos">the videos a person has uploaded or been tagged in</a>.</p>
     * <h4 id="review-user_videos">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     *
     */
    public static final String USER_VIDEOS = "user_videos";

    /**
     * <p>Provides access to the person's personal website URL via the <code>website</code> field on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_website">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_WEBSITE = "user_website";

    /**
     * <p>Provides access to a person's work history and list of employers via the <code>work</code> field on the <a href="/docs/graph-api/reference/user">User object</a>.</p>
     * <h4 id="review-user_work_history">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String USER_WORK_HISTORY = "user_work_history";

    /**
     * <p>Provides access to the names of custom lists a person has created to organize their friends. This is useful for rendering an audience selector when someone is publishing stories to Facebook from your app.</p>
     * <p><strong>This permission does not give access to a list of person's friends.</strong>  If you want to access a person's friends who also use your app, you should use the <a href="#reference-user_friends"><code>user_friends</code></a> permission.</p>
     * <p>This permission will also not help you invite a person's friends to use your app.  To learn more about how to invite friends to an app, please see our <a href="/docs/apps/faq">FAQs</a>.</p>
     * <p>This permission also does not give the list of friends who are part of a friendlist.  It only gives access to the names of the lists.</p>
     * <p>This permission was called <code>read_friendlists</code> before v2.3.</p>
     * <h4 id="review-read_custom_friendlists">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String READ_CUSTOM_FRIENDLISTS = "read_custom_friendlists";

    /**
     * <p>Provides read-only access to the Insights data for Pages, Apps and web domains the person owns.</p>
     * <h4 id="review-read_insights">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String READ_INSIGHTS = "read_insights";

    /**
     * <p>Provides the ability to read the messages in a person's Facebook Inbox through the <a href="/docs/graph-api/reference/user/inbox">inbox edge</a> and the <a href="/docs/graph-api/reference/thread">thread node</a>.</p>
     * <h4 id="review-read_mailbox">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String READ_MAILBOX = "read_mailbox";

    /**
     * <p>Provides the ability to read from the Page Inboxes of the Pages managed by a person. This permission is often used alongside the <code>manage_pages</code> permission.</p>
     * <p>This permission does not let your app read the page owner's mailbox.  It only applies to the page's mailbox.</p>
     * <h4 id="review-read_page_mailboxes">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String READ_PAGE_MAILBOXES = "read_page_mailboxes";

    /**
     * <p>Provides access to read the posts in a person's News Feed, or the posts on their Profile.</p>
     * <h4 id="review-read_stream">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String READ_STREAM = "read_stream";

    /**
     * <p>Enables your app to read a person's notifications and mark them as read.</p>
     * <p>This permission does not let you send notifications to a person.</p>
     * <h4 id="review-manage_notifications">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String MANAGE_NOTIFICATIONS = "manage_notifications";

    /**
     * <p>Enables your app to retrieve Page Access Tokens for the Pages and Apps that the person administrates.</p>
     * <p>Apps need both <code>manage_pages</code> and <code>publish_pages</code> to be able to publish as a Page.</p>
     * <h4 id="review-manage_pages">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it. You can grant this permission on behalf of people listed within the Roles section of your App's Dashboard without review by Facebook.</p>
     * <p>If you want to use the <a href="/docs/wordpress/">Facebook Plugin for Wordpress</a> (or a similar tool) to publish blog posts to your Facebook page, <strong>you do not need to submit for review as long as all those for whom you want to publish are listed in the Roles section of your App's Dashboard.</strong></p>
     * <p>When submitting for review, please make sure your instructions are easily reproducible by our team. For example, if your Page Management Tool has its own authentication system, please ensure you provide a working login (such as a username/password) to allow our review team to use your tool and test this functionality.</p>
     */
    public static final String MANAGE_PAGES = "manage_pages";

    /**
     * <p>When you also have the <code>manage_pages</code> permission, gives your app the ability to post, comment and like as any of the Pages managed by a person using your app.</p>
     * <p>Apps need both <code>manage_pages</code> and <code>publish_pages</code> to be able to publish as a Page.</p>
     * <p>Publishing as an individual personal account is not possible with this permission. To post as an individual, please see the <code>publish_actions</code> permission.</p>
     * <h4 id="review-publish_pages">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     * <p>When requesting this permission via App Review, please make sure your instructions are easily reproducible by our team.</p>
     */
    public static final String PUBLISH_PAGES = "publish_pages";

    /**
     * <p>Provides access to publish <a href="https://developers.facebook.com/docs/graph-api/reference/post">Posts</a>, Open Graph actions, achievements, scores and other activity on behalf of a person using your app.</p>
     * <p>Because this permission lets you publish on behalf of a user please read the <a href="/policy">Platform Policies</a> to ensure you understand how to properly use this permission.</p>
     * <p>Your app does <strong>not</strong> need to request the <code>publish_actions</code> permission in order to use the <a href="/docs/reference/dialogs/feed">Feed Dialog</a>, the <a href="/docs/reference/dialogs/requests">Requests Dialog</a> or the <a href="/docs/reference/dialogs/send">Send Dialog</a></p>
     * <h4 id="review-publish_actions">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     * <p>When requesting this permission via App Review, please make sure your instructions are easily reproducible by our team.</p>
     */
    public static final String PUBLISH_ACTIONS = "publish_actions";

    /**
     * <p>Provides the ability to set a person's attendee status on Facebook <a href="/docs/graph-api/reference/event">Events</a> (e.g. <a href="/docs/graph-api/reference/event/attending">attending</a>, <a href="/docs/graph-api/reference/event/maybe">maybe</a>, or <a href="/docs/graph-api/reference/event/declined">declined</a>).</p>
     * <p>This permission does not let you invite people to an event.</p>
     * <p>This permission does not let you update an event's details.</p>
     * <p>This permission does not let you create an event.  There is no way to create an event via the API as of Graph API v2.0.</p>
     * <h4 id="review-rsvp_event">Review</h4>
     * <p>If your app requests this permission Facebook will have to <a href="/docs/apps/review/login">review</a> how your app uses it.</p>
     */
    public static final String RSVP_EVENT = "rsvp_event";

    /**
     * A Permission which acts as null value which is never granted.
     */
    private static final Permission NULL = new Permission("", "declined");;

    /**
     * Creates a FBPermissions object from a list of FBPermission objects.
     *
     * @param permissionList the list of FBPermission objects.
     *
     * @return the new created FBPermissions object.
     */
    public static FBPermissions from(List<Permission> permissionList) {
        FBPermissions permissions = new FBPermissions();
        permissionList.forEach((permission -> {
            permissions.permissions.put(permission.getName(), permission);
        }));
        return permissions;
    }


    /**
     * Creates a FBPermissions object from a list of Strings which describes facebook permissions.
     *
     * @param permissionList the list of FBPermission objects.
     *
     * @return the new created FBPermissions object.
     */
    public static FBPermissions fromStringList(List<String> permissionList) {
        FBPermissions permissions = new FBPermissions();

        permissionList.forEach((permission -> {
            permissions.permissions.put(permission, new Permission(permission, "granted"));
        }));
        return permissions;
    }

    protected Map<String, Permission> permissions = null;

    /**
     * Create a empty permission object.
     */
    public FBPermissions() {
        permissions = new HashMap<>();
    }

    /**
     * Check if a given list of permission were granted by the owner.
     *
     * @param permissions the name of permission.
     * @return true if all given permissions are granted by the owner.
     */
    public boolean hasGranted(String ...permissions) {
        return hasGranted(Arrays.asList(permissions));
    }

    /**
     * Check if a given list of permission were granted by the owner.
     *
     * @param permissions the name of permission.
     * @return true if all given permissions are granted by the owner.
     */
    public boolean hasGranted(List<String> permissions) {
        return permissions.stream().allMatch((permission) -> hasGranted(permission));
    }

    /**
     * Check if a given list of permission were granted by the owner.
     *
     * @param permissions the name of permission.
     * @return true if all given permissions are granted by the owner.
     */
    public List<String> nonGrantedPermissions(List<String> permissions) {
        return permissions.stream().filter((permission) -> !hasGranted(permission)).collect(Collectors.toList());
    }

    /**
     * Check if a given permission was granted by the owner.
     *
     * @param permission the name of the permission.
     * @return true if the given permission was granted by the owner.
     */
    public boolean hasGranted(String permission) {

        return permissions.getOrDefault(permission, NULL).isGranted();
    }


}
