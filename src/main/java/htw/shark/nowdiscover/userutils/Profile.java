package htw.shark.nowdiscover.userutils;

import java.util.ArrayList;

/**
 * Implements a user Profile containing the user's interests, also has methods
 * to add change or delete interests
 *
 * @author Jörn Sattler s0542818
 */
public class Profile implements Profile_Interface {

	ArrayList<Interest> Profile = new ArrayList<Interest>();

	/**
	 * Konstruktor: requires an ArrayList<Interest> of interests
	 *
	 * @param Profile
	 */
	public Profile(ArrayList<Interest> Profile) {
		this.Profile = Profile;
	}

	/**
	 * Konstruktor:
	 *
	 * @param interest
	 *            requires one Interest Object
	 */
	public Profile(Interest interest) {
		Profile.add(interest);
	}

	public Profile() {

	}

	/**
	 * Adds an interest to a user's profile
	 *
	 * @param interest
	 */
	@Override
	public void addInterest(Interest interest) {
		Profile.add(interest);
	}

	/**
	 * Adds an ArrayList containing a number of Interests to a user's profile
	 *
	 * @param Interests
	 *            ArrayList: Interest Object
	 */
	@Override
	public void addInterest(ArrayList<Interest> Interests) {
		for (Interest interest : Interests) {
			Profile.add(interest);
		}
	}

	/**
	 * Returns the Profile
	 *
	 * @return Object: Profile
	 */
	@Override
	public Profile getProfile() {
		return Profile.this;
	}

	/**
	 * Returns a given Interest Object by Name
	 *
	 * @param name
	 *            Name of the Interest you wish to return. Returns null if given
	 *            Interestname doesn´t exist
	 * @return
	 */
	@Override
	public Interest getInterest(String name) {
		for (Interest interest : Profile) {
			if (name.equals(interest.getTopicname())) {
				return interest;
			}
		}
		return null;
	}

	/**
	 * Returns all the Profile's Interests as an List
	 *
	 * @return ArrayList Interest
	 */
	@Override
	public ArrayList<Interest> getInterest() {
		return Profile;
	}

	/**
	 * Returns the Profile's UserID as String
	 *
	 * @return String: UserID
	 */
	@Override
	public String getUserID() {
		if (Profile.isEmpty()) {
			return "Profile is empty";
		} else {
			Interest interest = Profile.get(Profile.size() - 1);
			return interest.getUserID();
		}
	}

	/**
	 * Removes a given Interest from the Profile
	 *
	 * @param interest
	 *            Interest Object you want to have removed
	 */
	@Override
	public void removeInterest(Interest interest) {
		Profile.remove(interest);
	}

}
