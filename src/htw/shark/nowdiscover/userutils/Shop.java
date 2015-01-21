package htw.shark.nowdiscover.userutils;

import htw.shark.nowdiscover.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.system.*;

public interface Shop {

	public Category createCategory(String name, String url)
			throws shopException, SharkException;

	public Category createCategory(String name, String... url)
			throws shopException, SharkException;

	public Product createProduct(String name, String url);

	public Product createProduct(String name, String... url);

	public User createUser(String name, String url);

	public void createProfile(User user);

	public Iterable<Profile> getProfiles() throws SharkKBException;

	public Profile getProfile();

	public void removeProfile(Profile profile);

	public void createInterest(ShopConcept shopConcept, Profile profile,
			String property, String value);

	public void removeInterest(Interest interest);

	public Interest getInterest();

}
