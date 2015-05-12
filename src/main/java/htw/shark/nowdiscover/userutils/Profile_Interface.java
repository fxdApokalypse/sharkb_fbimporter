package htw.shark.nowdiscover.userutils;

import java.util.ArrayList;

/**
 *
 * @author Jörn Sattler s0542818
 */
public interface Profile_Interface {

	public void addInterest(Interest interest);

	public void addInterest(ArrayList<Interest> Interests);

	public Interest getInterest(String name);

	public ArrayList<Interest> getInterest();

	public Profile getProfile();

	public String getUserID();

	public void removeInterest(Interest interest);

}
