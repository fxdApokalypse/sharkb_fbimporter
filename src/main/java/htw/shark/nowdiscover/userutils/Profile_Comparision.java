package htw.shark.nowdiscover.userutils;

import java.util.Iterator;

/**
 * Prototype class for comparing dedicated user profiles at the moment it uses a
 * very simple algorithm
 * 
 * @author Jörn Sattler s0542818
 */
public class Profile_Comparision implements Profile_Comparision_Interface {

	/**
	 * Compares two given user profiles if they have similiar interests and
	 * returns a number ranging from -1 to 1 the greater the number is the more
	 * similiar are the users
	 * 
	 * @param profile1
	 *            requires a Profile object
	 * @param profile2
	 *            requires a Profile object
	 * @return returns a double ranging from -1 to 1. The greater the number the
	 *         more similiar the profiles are
	 */
	@Override
	public double compareProfiles(Profile profile1, Profile profile2) {
		double rating = 0.0;
		double size = (double) (profile1.getInterest().size() + profile2.getInterest().size());
		Iterator<Interest> itera = profile1.getInterest().iterator();
		Iterator<Interest> iterb = profile2.getInterest().iterator();

		while (itera.hasNext()) {
			Interest tempInteresta = itera.next();
			while (iterb.hasNext()) {
				Interest tempInterestb = iterb.next();
				if (tempInteresta.getTopicname().equals(
						tempInterestb.getTopicname())) {
					rating = (Double.parseDouble(tempInteresta.getRating()) + Double
							.parseDouble(tempInterestb.getRating()));

				}

			}

		}

		return rating / size;
	}

}
