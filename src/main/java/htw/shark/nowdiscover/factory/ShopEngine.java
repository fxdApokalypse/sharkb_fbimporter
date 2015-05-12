package htw.shark.nowdiscover.factory;

import htw.shark.nowdiscover.dataimportutils.CSV;
import htw.shark.nowdiscover.dataimportutils.Facebook_Import;
import htw.shark.nowdiscover.shoputils.Category;
import htw.shark.nowdiscover.shoputils.Product;
import htw.shark.nowdiscover.shoputils.SharkCategory;
import htw.shark.nowdiscover.shoputils.SharkProduct;
import htw.shark.nowdiscover.shoputils.ShopConcept;
import htw.shark.nowdiscover.shoputils.ShopException;
import htw.shark.nowdiscover.userutils.Interest;
import htw.shark.nowdiscover.userutils.Profile;
import htw.shark.nowdiscover.userutils.User;
import htw.shark.util.Enumerations;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.sharkfw.knowledgeBase.ContextCoordinates;
import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Knowledge;
import net.sharkfw.knowledgeBase.SharkCSAlgebra;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

/**
 * The class is a factory for categories, products, users, profiles and
 * interests and has a Singelton KnowledgedeBase.
 *
 * @author Jörn Sattler s0542818, Ido Sternberg
 */
public class ShopEngine implements Shop {

	private static ShopEngine instance = null;

	private InMemoSharkKB kb;

	private List<Product> products;

	private List<Category> categories;

	private List<Profile> profiles;

	private List<User> users;

	private ShopEngine() {
		kb = new InMemoSharkKB();
		products = new ArrayList<>();
		categories = new ArrayList<>();
		profiles = new ArrayList<>();
		users = new ArrayList<>();
	}

	public static Shop getShopEngine() {
		if (instance == null) {
			instance = new ShopEngine();
		}
		return instance;
	}

	private InMemoSharkKB getKB() {
		return this.kb;
	}

	/**
	 * Creates a category within the knowledgebase
	 *
	 * @param name
	 *            of the category
	 * @param urls
	 *            expects a String-array of urls
	 * @return returns a new category
	 * @throws ShopException
	 */
	@Override
	public Category createCategory(String name, String... urls) throws ShopException {
		Category category;
		try {
			category = new SharkCategory(this.getKB(), name, urls);
		} catch (SharkKBException e) {
			throw new ShopException(e);
		}
		categories.add(category);
		return category;
	}

	/**
	 * Returns an ArrayList of product-categories
	 *
	 * @return
	 */
	@Override
	public List<Category> getAllCategories() {
		return new ArrayList<>(categories);
	}

	/**
	 * Returns an ArrayList of products
	 *
	 * @return
	 */
	@Override
	public List<Product> getAllProducts() {
		return new ArrayList<>(products);
	}

	/**
	 * Removes a product or a category from the knowledgebase
	 *
	 * @param concepts
	 *            is the superclass of class product and class category and
	 *            therefor excepts both
	 * @throws ShopException
	 */
	@Override
	public void remove(ShopConcept... concepts) throws ShopException {

		for (ShopConcept concept : concepts) {
			try {
				getKB().removeSemanticTag(concept.getUrls());
			} catch (SharkKBException e) {
				throw new ShopException(e);
			}
			if (concept instanceof Category) {
				categories.remove(concept);
			} else if (concept instanceof Product) {
				products.remove(concept);
			} else {
				throw new IllegalArgumentException("Your parameter must be a product or a category.");
			}
		}
	}

	/**
	 * Removes all categories from the knowledgebase
	 *
	 * @throws ShopException
	 */
	@Override
	public void removeAllCategories() throws ShopException {
		List<Category> allCategories = getAllCategories();
		for (Category category : allCategories) {
			remove(category);
		}

	}

	/**
	 * Removes all products from the knowledgebase
	 *
	 * @throws ShopException
	 */
	@Override
	public void removeAllProducts() throws ShopException {
		List<Product> allProducts = getAllProducts();
		for (Product product : allProducts) {
			remove(product);
		}
	}

	/**
	 * Creates a product in the knowledgebase.
	 *
	 * @param name
	 *            contains the name of the product
	 * @param url
	 *            contains the String-array of urls that identify a product
	 * @return returns the product
	 * @throws ShopException
	 */
	@Override
	public Product createProduct(String name, String... url) throws ShopException {
		Product product;
		try {
			product = new SharkProduct(getKB(), name, url);
		} catch (SharkKBException e) {
			throw new ShopException(e);
		}
		products.add(product);
		return product;
	}

	/**
	 * Returns an existing Profile otherwise returns null when no user with the
	 * given userID exists
	 *
	 * @param userid
	 *            String: Specified userID used to parse all existing users
	 * @return
	 */
	@Override
	public Profile getProfile(String userid) {

		for (Profile profile : profiles) {
			if (profile.getUserID().equals(userid)) {
				return profile;
			}
		}

		return null;
	}

	/**
	 * Returns an existing Profile belonging to a User Object, otherwise returns
	 * null if no Profile is found.
	 *
	 * @param user
	 *            Object: User object used to specifiy the Profile which has to
	 *            be returned
	 * @return
	 */
	@Override
	public Profile getProfile(User user) {
		return getProfile(user.getUserID());
	}

	/**
	 * Removes a given profile
	 *
	 * @param profile
	 *            requires Profile Object
	 */
	@Override
	public void removeProfile(Profile profile) {
		profiles.remove(profile);
		for (Interest interest : profile.getInterest()) {
			profile.removeInterest(interest);
		}
	}

	/**
	 * Returns an Interest Object specified by a name and a userID as String
	 * otherwise returns null
	 *
	 * @param name
	 *            requires String: topicname of the interest
	 * @param userID
	 *            requires String: userID
	 * @return returns the interest object or null
	 * @throws ShopException
	 */
	@Override
	public Interest getInterest(String name, String userID) throws ShopException {
		Knowledge knowledge = null;
		try {
			knowledge = SharkCSAlgebra.extract(kb, null);
		} catch (SharkKBException ex) {
			throw new ShopException(ex);
		}

		Enumeration<ContextPoint> contextPoints = knowledge.contextPoints();
		for (ContextPoint contextPoint : Enumerations.asIterable(contextPoints)) {
			ContextCoordinates contextCoords = contextPoint.getContextCoordinates();

			if (name.equals(contextCoords.getTopic().getName()) && userID.equals(contextCoords.getPeer().getName())) {
				return new Interest(contextPoint);
			}
		}

		return null;
	}

	/**
	 * Creates a user object, requires a facebook accesstoken (see facebook
	 * import class for tutorials on how to receive)
	 *
	 * @param accesstoken
	 *            requires String : requires a valid facebook
	 *            accesstoken
	 * @return returns the newly created user
	 * @throws ShopException
	 */
	@Override
	public User createUser(String accesstoken) throws ShopException {
		Facebook_Import importuser = null;
		User user = null;
		try {
			importuser = new Facebook_Import(accesstoken);
			user = new User(kb, importuser.getUserID(), importuser.getProfilelink());
		} catch (SharkKBException ex) {
			throw new ShopException(ex);
		}

		user.setAge(importuser.getAge());
		user.setBirthdate(importuser.getBirthdate());
		user.setGender(importuser.getGender());
		user.setInterestedIn(importuser.getInterestedIn());
		user.setIsinRelationship(importuser.getIsinRelationship());
		user.setResidence(importuser.getResidence());
		user.setLikes(importuser.getLikes());

		users.add(user);
		return user;
	}

	/**
	 * Creates a user Profile
	 *
	 * @param interest
	 *            requires interest object
	 * @return Object Profile
	 */
	@Override
	public Profile createProfile(Interest interest) {
		Profile profile = new Profile(interest);
		profiles.add(profile);
		return profile;
	}

	/**
	 * Creates an Interest belonging to a user
	 *
	 * @param shopConcept
	 *            requires a Product, a Category or an general Topic
	 * @param user
	 *            requires a given user Object to specify to whom the interest
	 *            belongs
	 * @param rating
	 *            requires a double (at the moment for simplicity purposes)
	 *            indicating how much the user is interested in the topic e.g. -1 hates, 1
	 *            likes it
	 * @return Object: Interest
	 * @throws ShopException
	 */
	@Override
	public Interest createInterest(ShopConcept shopConcept, User user, double rating) throws ShopException {
		Interest interest;
		try {
			interest = new Interest(kb.getSemanticTag(shopConcept.getUrls()), kb.getPeerSemanticTag(user.getFacebookProfileURL()), Double.toString(rating), kb);
		} catch (SharkKBException ex) {
			throw new ShopException(ex);
		}
		return interest;
	}

	/**
	 * Removes an Interest from a given existing Profile
	 *
	 * @param interest
	 *            Object: the interest you want to remove
	 * @param profile
	 *            Object: Profile that is affected
	 */
	@Override
	public void removeInterest(Interest interest, Profile profile) throws ShopException {

		profile.removeInterest(interest);
		Knowledge knowledge;
		try {
			knowledge = SharkCSAlgebra.extract(kb, null);
		} catch (SharkKBException ex) {
			throw new ShopException(ex);
		}

		Enumeration<ContextPoint> contextPoints = knowledge.contextPoints();
		for (ContextPoint contextPoint : Enumerations.asIterable(contextPoints)) {
			ContextCoordinates contextCoords = contextPoint.getContextCoordinates();

			if (interest.getTopicname().equals(contextCoords.getTopic().getName()) && interest.getUserID().equals(contextCoords.getPeer().getName())) {
				try {
					kb.removeContextPoint(contextCoords);
				} catch (SharkKBException ex) {
					throw new ShopException(ex);
				}
				break;
			}
		}
	}

	/**
	 * Returns all existing users
	 *
	 * @return List<User> returns a List containing all existing users
	 */
	@Override
	public List<User> getAllUsers() {
		return new ArrayList<>(users);
	}

	/**
	 * Returns all existing Profiles
	 *
	 * @return List<Profil> containing all existing Profiles
	 */
	@Override
	public List<Profile> getAllProfiles() {
		return new ArrayList<>(profiles);
	}

	/**
	 * Removes all existing users. Note: using this won´t remove the Profiles so
	 * handle with care!
	 *
	 * @throws ShopException
	 */
	@Override
	public void removeAllUsers() throws ShopException {
		for (User user : users) {
			removeUser(user);
		}
	}

	/**
	 * Deletes all existing Profiles
	 *
	 * @throws ShopException
	 */
	@Override
	public void remopveAllProfiles() throws ShopException {
		for (Profile profile : profiles) {
			removeProfile(profile);
		}
	}

	/**
	 * Returns a user object specified by a String
	 *
	 * @param userID
	 *            String: userID
	 * @return
	 */
	@Override
	public User getUser(String userID) {
		for (User user : users) {
			if (user.getUserID().equals(userID)) {
				return user;
			}

		}
		return null;
	}

	/**
	 * Removes a user from the system
	 *
	 * @param user
	 *            Object: user , requires a user object to specify wich user
	 *            will be deleted
	 * @throws ShopException
	 */
	@Override
	public void removeUser(User user) throws ShopException {
		users.remove(user);
		try {
			kb.removeSemanticTag(user.getFacebookProfileURL());
		} catch (SharkKBException ex) {
			throw new ShopException(ex);
		}
		user = null;

	}

	/**
	 * Exports basic knowledgebase-data into CSV files
	 */
	@Override
	public void exportCSV() {
		CSV export = new CSV();

		export.exportCSV(users, profiles, products, categories);
	}

	/**
	 * Imports CSV files specified by the filenames Note: files must be in
	 * current working directory
	 *
	 * @param filename
	 *            String: filename of the .CSV file which you want to
	 *            import
	 * @throws ShopException
	 */
	@Override
	public void importuserCSV(String filename) throws ShopException {
		boolean switch1 = false;
		boolean switch2 = false;
		String temp = null;
		String temp2;
		CSV import_CSV = new CSV();
		ArrayList<String> userinfo;
		int i = 0;
		List<List<String>> importlist = import_CSV.importCSV(filename);

		for (List<String> importstring_list : importlist) {
			i = 0;
			userinfo = new ArrayList<>();
			try {
				User user = new User(kb, importstring_list.get(0), importstring_list.get(1));
				Profile profile = new Profile();
				for (String importstring : importstring_list) {

					i++;
					if (i >= 3) {
						if (importstring.startsWith("|")) {
							switch1 = true;

							continue;

						}
						if (i >= 3 && switch1) {
							temp = importstring;

							switch1 = false;
							switch2 = true;
							continue;
						}

						if (i >= 3 && !switch1 && switch2) {
							temp2 = importstring;
							switch1 = true;
							switch2 = false;

							for (Product product : products) {
								if (temp.equals(product.getName())) {

									profile.addInterest(createInterest(product, user, Double.parseDouble(temp2)));
								}
							}
							for (Category category : categories) {
								if (temp.equals(category.getName())) {
									profile.addInterest(createInterest(category, user, Double.parseDouble(temp2)));
								}
							}
							continue;
						}

						userinfo.add(importstring);
					}
				}
				user.importUserInformation(userinfo);
				users.add(user);
				profiles.add(profile);
				switch1 = false;
				switch2 = false;
			} catch (SharkKBException ex) {
				throw new ShopException(ex);
			}

		}

	}

	/**
	 * Imports CSV files specified by the filenames Note: files must be in
	 * current working directory
	 *
	 * @param categorycsv
	 *            String: filename of the .CSV file which you want to
	 *            import
	 * @param productcsv
	 *            String: filename of the .CSV file which you want to
	 *            import
	 * @throws ShopException
	 */
	@Override
	public void importproductCSV(String categorycsv, String productcsv) throws ShopException {
		CSV import_CSV = new CSV();

		int i = 0;
		List<List<String>> importlist = import_CSV.importCSV(categorycsv);

		for (List<String> importstring_list : importlist) {
			System.out.println();
			i = 0;
			try {
				Category category = new SharkCategory(kb, importstring_list.get(0), importstring_list.get(1));
				for (String importstring : importstring_list) {
					i++;
					if (i >= 3) {
						if (importstring.startsWith("|")) {
							break;
						}
						category.addUrls(importstring);

					}

				}

				categories.add(category);

			} catch (SharkKBException ex) {
				throw new ShopException(ex);
			}

			List<List<String>> importlistb = import_CSV.importCSV(productcsv);
			List<Category> importcat;
			List<Product> importprod;
			for (List<String> importstring_listb : importlistb) {

				i = 0;
				boolean switch1 = false;
				boolean switch2 = false;
				try {
					Product product = new SharkProduct(kb, importstring_listb.get(0), importstring_listb.get(1));

					for (String importstring : importstring_listb) {
						i++;
						if (i >= 3) {

							if (importstring.startsWith("|") && !switch1) {
								switch1 = true;
								continue;

							}

							if (switch1 && importstring.startsWith("|")) {
								switch2 = true;
								continue;
							}

							if (switch2) {
								for (Product prodtemp : products) {
									if (prodtemp.getName().equals(importstring)) {
										importprod = new ArrayList<>();
										importprod.add(prodtemp);
										product.addRelatedProducts(importprod);
									}
								}
								continue;
							}

							if (switch1) {

								for (Category cattemp : categories) {
									if (cattemp.getName().equals(importstring)) {
										importcat = new ArrayList<>();
										importcat.add(cattemp);
										product.addCategories(importcat);
									}
								}
								continue;
							}

							product.addUrls(importstring);

						}

					}

					products.add(product);

				} catch (SharkKBException ex) {
					throw new ShopException(ex);
				}

			}
		}
	}
}
