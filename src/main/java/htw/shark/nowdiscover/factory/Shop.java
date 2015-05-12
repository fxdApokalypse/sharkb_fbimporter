package htw.shark.nowdiscover.factory;

import htw.shark.nowdiscover.shoputils.Category;
import htw.shark.nowdiscover.shoputils.Product;
import htw.shark.nowdiscover.shoputils.ShopConcept;
import htw.shark.nowdiscover.shoputils.ShopException;
import htw.shark.nowdiscover.userutils.Interest;
import htw.shark.nowdiscover.userutils.Profile;
import htw.shark.nowdiscover.userutils.User;

import java.util.List;

public interface Shop {

	public Category createCategory(String name, String... url) throws ShopException;

	public void remove(ShopConcept... concept) throws ShopException;

	public void removeAllCategories() throws ShopException;

	public void removeAllProducts() throws ShopException;

	public Product createProduct(String name, String... url) throws ShopException;

	public List<Category> getAllCategories();

	public List<Product> getAllProducts();

	public List<User> getAllUsers();

	public List<Profile> getAllProfiles();

	public void removeAllUsers() throws ShopException;

	public void remopveAllProfiles() throws ShopException;

	public User createUser(String accestoken) throws ShopException;

	public User getUser(String userID);

	public void removeUser(User user) throws ShopException;

	public Profile createProfile(Interest interest);

	public Profile getProfile(User user);

	public Profile getProfile(String userid);

	public void removeProfile(Profile profile);

	public Interest createInterest(ShopConcept shopConcept, User user, double rating)
	        throws ShopException;

	public void removeInterest(Interest interest, Profile Profile) throws ShopException;

	public Interest getInterest(String userid, String interestname) throws ShopException;

	public void exportCSV();

	public void importuserCSV(String filename) throws ShopException;

	public void importproductCSV(String filenamea, String filenameb) throws ShopException;

}
