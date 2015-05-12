package htw.shark.nowdiscover.dataimportutils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sharkfw.knowledgeBase.SharkKBException;

import org.joda.time.DateTime;
import org.joda.time.Years;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.User;

/**
 * Uses the facebook GraphAPI and the restFB library to import userdata from FB:
 * general information like age, date of birth, name , userid also imports the
 * user's Likes (books, movies, general likes, groups, the user is member of
 * etc.) requires an access token generated
 * 
 * https://developers.facebook.com/tools/explorer
 * 
 * The restFB library allows to easily add methods to fetch other
 * information
 * like family members etc. Please check the website for guides:
 * http://restfb.com/#fetching
 * 
 * The goal of this method is to allow an app, via the accesstokens, to
 * recceive
 * information about the user. The possibilities of information we can
 * gather is
 * nearly endless. It´s only limited by the rights the user gives us
 * when adding
 * an app on facebook or logging in to a website with his facebook
 * account.
 * 
 * @author Jörn Sattler s0542818, Alexander Ihm s0543565
 */
public class Facebook_Import implements Facebook_Import_Interface {

	private FacebookClient facebookClient;

	private User user;

	ArrayList<String> likes;

	/**
	 * Facebook-import-function
	 * 
	 * @param accessToken
	 *            a valid facebook access token as String
	 * @throws SharkKBException
	 */
	public Facebook_Import(String accessToken) throws SharkKBException {

		facebookClient = new DefaultFacebookClient(accessToken);
		user = facebookClient.fetchObject("me", User.class);
		likes = new ArrayList<String>();

		Connection<Page> fetchConnection = facebookClient.fetchConnection("me/likes", Page.class);
		// TODO: REFACTORE ME: eliminate duplication
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

		fetchConnection = facebookClient.fetchConnection("me/books", Page.class);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

		fetchConnection = facebookClient.fetchConnection("me/games", Page.class);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

		fetchConnection = facebookClient.fetchConnection("me/groups", Page.class);
		for (Page page : fetchConnection.getData()) {

			likes.add(page.getName());

		}
		fetchConnection = facebookClient.fetchConnection("me/movies", Page.class);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

		fetchConnection = facebookClient.fetchConnection("me/television", Page.class);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

		fetchConnection = facebookClient.fetchConnection("me/videos", Page.class);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

	}

	/**
	 * Returns the given access token's/facebook profile's age
	 * 
	 * @return returns the given access token's/facebook profile's age as int
	 */
	@Override
	public int getAge() {
		Date d1 = user.getBirthdayAsDate();
		Date d2 = new Date(System.currentTimeMillis());
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		Years y1 = Years.yearsBetween(dt1, dt2);

		int age = y1.getYears();

		if (dt2.getMonthOfYear() >= dt1.getMonthOfYear() && dt2.getDayOfMonth() >= dt1.getDayOfMonth()) {
			age++;

		}
		return age;
	}

	/**
	 * Returns the given access token's/facebook profile's Date of Birth
	 * 
	 * @return returns the given access token's/facebook profile's Date of birth
	 *         as Date
	 */
	@Override
	public Date getBirthdate() {

		return user.getBirthdayAsDate();

	}

	/**
	 * Returns the given access token's/facebook profile's residence
	 * 
	 * @return returns the given access token's/facebook profile's residence as
	 *         String
	 */
	@Override
	public String getResidence() {
		return user.getHometownName();
	}

	/**
	 * Returns the given access token's/facebook profile's gender
	 * 
	 * @return returns the given access token's/facebook profile's gender as
	 *         String
	 */
	@Override
	public String getGender() {
		return user.getGender();
	}

	/**
	 * Returns the given access token's/facebook profile's sexual preference
	 * 
	 * @return returns the given access token's/facebook profile's age as
	 *         String, returns male, female, male-female or unknown
	 */
	@Override
	public String getInterestedIn() {

		String preferences = "unbekannt";
		List<String> interestedlist = user.getInterestedIn();

		if (interestedlist.size() == 1) {

			if ("male".equals(interestedlist.get(0))) {
				preferences = "male";

			} else if ("female".equals(interestedlist.get(0))) {
				preferences = "female";

			} else {
				preferences = "unknown";
			}
		}

		if (interestedlist.size() == 2) {

			preferences = "male&female";

		}

		return preferences;
	}

	/**
	 * Returns the given access token's/facebook profile's relationship status
	 * 
	 * @return returns the given access token's/facebook profile's relationship
	 *         status as String
	 */
	@Override
	public String getIsinRelationship() {

		return user.getRelationshipStatus();

	}

	/**
	 * Returns the given access token's/facebook profile's userID
	 * 
	 * @return returns the given access token's/facebook profile's userID as
	 *         String
	 */
	@Override
	public String getUserID() {
		return user.getId();

	}

	/**
	 * Returns the given access token's/facebook profile's profile link
	 * 
	 * @return returns the given access token's/facebook profile's profile link
	 *         as String
	 */
	@Override
	public String getProfilelink() {
		return user.getLink();
	}

	/**
	 * Returns the given access tokens'/facebook profile's likes
	 * 
	 * @return returns the given access token's/facebook profile's likes as
	 *         List<String>
	 */
	@Override
	public List<String> getLikes() {
		return likes;
	}

}
