package htw.shark.nowdiscover.userutils;

/**
 * Class creates a User containing information
 * gathered with the Facebook Graph Api
 * 
 * @author Jörn Sattler s0542818, Claus Holland s0542708, Alexander Ihm s0543565
 *
 */
import htw.shark.nowdiscover.errors.InformationUserInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;

public class User implements User_Interface {

	private PeerSemanticTag user = null;

	/**
	 * Konstruktor:
	 *
	 * @param kb
	 *            SharkFramework Requirement Used to Store Information
	 * @param userID
	 *            String: Facebook access token of the dedicated user
	 * @param profilelink
	 *            String: Url which specifies the User on Facebook
	 * @throws SharkKBException
	 */
	public User(SharkKB kb, String userID, String profilelink) throws SharkKBException {
		user = kb.createPeerSemanticTag(userID, profilelink, "valid_profile_export");
	}

	/**
	 * Returns the User Oject
	 *
	 * @return Returns the User Object
	 */
	@Override
	public User getUser() {
		return User.this;
	}

	/**
	 * Returns the users Id as String
	 *
	 * @return String: Returns the Users Facebook ID
	 */
	@Override
	public String getUserID() {
		return user.getName();
	}

	/**
	 * Allows changing of the users unique id
	 *
	 * @param name
	 *            String: Sets the Users unique facebook ID
	 */
	@Override
	public void setUserID(String name) {
		user.setName(name);
	}

	/**
	 * Allows changing of the users Facebook profile link
	 *
	 * @param suis
	 *            String[] requires String[] because of the SharkFramework's nature but
	 *            only contains one entry
	 * @throws SharkKBException
	 */
	@Override
	public void setFacebookProfileURL(String... suis) throws SharkKBException {
		for (String sui : suis) {
			user.addSI(sui);
		}
	}

	/**
	 * Returns the users Facebook Profile Link as String[]
	 *
	 * @return String[] containing the users profile link
	 */
	@Override
	public String[] getFacebookProfileURL() {
		return user.getSI();

	}

	/**
	 * Returns all saved information as a String[]
	 *
	 * @return String[]: Returns all saved User Information
	 */
	@Override
	public String[] getUserInformation() {
		String[] defaultstring = new String[1];
		defaultstring[0] = "No information about this user yet!";
		try {
			if (user.getAddresses() != null) {
				return user.getAddresses();
			} else {
				throw new InformationUserInfo();
			}
		} catch (InformationUserInfo e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return defaultstring;
		}

	}

	/**
	 * Returns Integer of the User's age
	 * 
	 * @return returns the users age as int
	 */
	@Override
	public int getAge() {

		int kint = 0;
		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("age;!")) {
				String[] k = s.split(";!");
				kint = Integer.parseInt(k[1]);
				return kint;
			}
		}
		return kint;
	}

	/**
	 * Sets the age for the current user
	 * 
	 * @param age
	 *            requires int
	 * @return returns age as int
	 */
	@Override
	public int setAge(int age) {

		Integer.toString(age);
		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("age;!")) {
				user.removeAddress(s);
				user.addAddress("age;!" + age);
			} else {
				user.addAddress("age;!" + age);
			}
		}
		return age;
	}

	/**
	 * Requieres Date, sets the users birthdate
	 * 
	 * @param birthdate
	 *            requires Date
	 */
	@Override
	public void setBirthdate(Date birthdate) {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("birthdate;!")) {
				user.removeAddress(s);
				user.addAddress("birthdate;!" + birthdate.toString());
			} else {
				user.addAddress("birthdate;!" + birthdate.toString());
			}
		}
	}

	/**
	 * Returns the users day of birth
	 * 
	 * @return returns the users day of birth as string
	 */
	@Override
	public String getBirthdate() {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("birthdate;!")) {
				String[] k = s.split(";!");
				return k[1];
			}
		}
		return "No birthdate set.";

	}

	/**
	 * Sets the user's hometown
	 * 
	 * @param residence
	 *            requires String to set the user's current hometown
	 */
	@Override
	public void setResidence(String residence) {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("residence;!")) {
				user.removeAddress(s);
				user.addAddress("residence;!" + residence);
			} else {
				user.addAddress("residence;!" + residence);
			}
		}

	}

	/**
	 * Returns the user's hometown
	 * 
	 * @return returns String containing the user's hometown
	 */
	@Override
	public String getResidence() {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("residence;!")) {
				String[] k = s.split(";!");
				return k[1];
			}
		}
		return "No residence set.";

	}

	/**
	 * Sets the current user's gender
	 *
	 * @param gender
	 *            requires String containing the user's gender
	 */
	@Override
	public void setGender(String gender) {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("gender;!")) {
				user.removeAddress(s);
				user.addAddress("gender;!" + gender);
			} else {
				user.addAddress("gender;!" + gender);
			}
		}

	}

	/**
	 * Returns the user's gender
	 * 
	 * @return returns the current user's gender as String
	 */
	@Override
	public String getGender() {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("gender;!")) {
				String[] k = s.split(";!");
				return k[1];
			}
		}
		return "No gender set.";

	}

	/**
	 * Sets the user's preference e.g. male/female/male-female
	 * 
	 * @param preference
	 *            requires String
	 */
	@Override
	public void setInterestedIn(String preference) {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("preference;!")) {
				user.removeAddress(s);
				user.addAddress("preference;!" + preference);
			} else {
				user.addAddress("preference;!" + preference);
			}
		}
	}

	/**
	 * Returns the user's preference as String
	 * 
	 * @return returns male/female or male-female
	 */
	@Override
	public String getInterestedIn() {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("preference;!")) {
				String[] k = s.split(";!");
				return k[1];
			}
		}
		return "No preference set.";

	}

	/**
	 * Sets if the user is in an relationship
	 * 
	 * @param relationship
	 *            requires string
	 */
	@Override
	public void setIsinRelationship(String relationship) {

		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("relationshoip;!")) {
				user.removeAddress(s);
				user.addAddress("relationship;!" + relationship);
			} else {
				user.addAddress("relationship;!" + relationship);
			}
		}
	}

	/**
	 * Returns the relationship status of the current user
	 * 
	 * @return returns yes or no as String
	 */
	@Override
	public String getIsinRelationship() {
		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("relationship;!")) {
				String[] k = s.split(";!");
				return k[1];
			}
		}
		return "No relationship status set.";
	}

	/**
	 * Sets the current user's likes
	 * 
	 * @param Likes
	 *            requieres List<String> containing the likes of the user
	 */
	@Override
	public void setLikes(List<String> Likes) {
		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("like;!")) {
				user.removeAddress(s);
			}
			for (String like : Likes) {
				user.addAddress("like;!" + like);
			}

		}
	}

	/**
	 * Returns an ArrayList containing Strings. If the user has no likes it returns an empty list
	 * 
	 * @return returns an ArrayList with the user's current facebook likes as Strings
	 */
	@Override
	public List<String> getLikes() {
		ArrayList<String> Likes = new ArrayList<>();
		String[] infos = user.getAddresses();
		for (String s : infos) {
			if (s.startsWith("like;!")) {
				String[] k = s.split(";!");
				Likes.add(s);
			}
		}
		if (Likes.isEmpty()) {
			Likes.add("The given user has no likes");
			return Likes;
		}
		return Likes;

	}

	/**
	 * Imports userinformation from a csv. Only use this with our CSV export
	 * 
	 * @param userinfo
	 */
	@Override
	public void importUserInformation(List<String> userinfo) {
		for (String info : userinfo) {
			user.addAddress(info);

		}
	}

}
