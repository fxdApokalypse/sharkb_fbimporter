package htw.shark.nowdiscover.dataimportutils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import net.sharkfw.knowledgeBase.SharkKBException;

import org.apache.commons.lang3.time.StopWatch;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
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
 * @author Jörn Sattler s0542818, Alexander Ihm s0543565, Yves Kaufmann
 */
public class Facebook_Import implements Facebook_Import_Interface {
	


	public static void main(String[] args) throws SharkKBException {
		Facebook_Import importer = new Facebook_Import("CAACEdEose0cBAF9dKj21kCvPMK1gHO7bw0pvSDpj2cEUiFPagQnOu63g8LLoBHg9rOS8ZCcooMMSm6wbKyiNCfTKYYjzgGdmtRMicSEc75qS1ZB2JRQxsXDqQVUx7MbCHN7PjdinNmAU25h4ZCZAXNHv0lOd03OtNR2nyBxsvWAJ33q90Cnbz68p0Ftz49W46KZCDsoZARyuHNUi8mrIcsANIAglAxW6IZD");
		System.out.println(importer.getAge());
	}
	
	private FacebookClient facebookClient;

	private User user;

	List<String> likes;

	/**
	 * Facebook-import-function
	 * 
	 * @param accessToken
	 *            a valid facebook access token as String
	 * @throws SharkKBException
	 */
	public Facebook_Import(String accessToken) throws SharkKBException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		// fetchLikesNonBatch(accessToken);
		fetchLikesWithBatch(accessToken);
		sw.stop();
		System.out.println("Count = " + likes.size());
		System.out.println("Output = " + likes);
		System.out.println(sw.getTime());
	}

	private void fetchLikesWithBatch(String accessToken) {
		
		facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_3);
		user = facebookClient.fetchObject("me", User.class);
		
		List<BatchRequest> batchLikeRequests = Arrays
            .asList("likes", "books", "games", "groups", "movies", "television", "videos")
            .stream()
            .map((edge) -> new BatchRequest.BatchRequestBuilder( "me/" + edge + "?fields=id,name,link" ).build())
            .collect(Collectors.toList());

        JsonMapper jsonMapper = new DefaultJsonMapper();

        List<BatchResponse> responses = facebookClient.executeBatch(batchLikeRequests);

        likes = responses
            .stream()
            .flatMap((response) -> jsonMapper.toJavaList(response.getBody(), Like.class).stream())
            .map((like) -> String.format("%s:%s - %s", like.getId(), like.getName(), like.getLink()))
            .collect(Collectors.toList());
	}

	private void fetchLikesNonBatch(String accessToken) {
		facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_3);
		user = facebookClient.fetchObject("me", User.class);
		likes = new ArrayList<String>();
		
		Parameter onlyName = Parameter.with("fields", "name");
		Connection<Page> fetchConnection = facebookClient.fetchConnection("me/likes", Page.class, onlyName);
		
		// TODO: REFACTORE ME: eliminate duplication
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}
		
		fetchConnection = facebookClient.fetchConnection("me/books", Page.class, onlyName);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}
		
		fetchConnection = facebookClient.fetchConnection("me/games", Page.class, onlyName);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}
		
		fetchConnection = facebookClient.fetchConnection("me/groups", Page.class, onlyName);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}
		
		fetchConnection = facebookClient.fetchConnection("me/movies", Page.class, onlyName);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}
		
		fetchConnection = facebookClient.fetchConnection("me/television", Page.class, onlyName);
		for (Page page : fetchConnection.getData()) {
			likes.add(page.getName());
		}

		fetchConnection = facebookClient.fetchConnection("me/videos", Page.class, onlyName);
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
		
		LocalDate dateOfBirth = user.getBirthdayAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate today = LocalDate.now();
		
		return (int) ChronoUnit.YEARS.between(dateOfBirth, today);
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
