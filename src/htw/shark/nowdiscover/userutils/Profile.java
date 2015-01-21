package htw.shark.nowdiscover.userutils;

import java.util.*;

public class Profile implements Profile_Interface {

	ArrayList<Interest> Profile = new ArrayList<Interest>();

	public Profile(ArrayList<Interest> Profile) {
		this.Profile = Profile;
	}

	public Profile() {
	}

	@Override
	public void addInterest(Interest interest) {
		Profile.add(interest);
	}

	@Override
	public void addInterest(ArrayList<Interest> Interests) {
		Iterator itr = Interests.iterator();
		while (itr.hasNext()) {
			Profile.add((Interest) itr.next());
		}

	}

	@Override
	public void removeInterest(Interest interest) {
		Profile.remove(interest);
	}

}
