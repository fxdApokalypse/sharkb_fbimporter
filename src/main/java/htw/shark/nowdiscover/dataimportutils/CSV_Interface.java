package htw.shark.nowdiscover.dataimportutils;

import htw.shark.nowdiscover.shoputils.Category;
import htw.shark.nowdiscover.shoputils.Product;
import htw.shark.nowdiscover.userutils.Profile;
import htw.shark.nowdiscover.userutils.User;

import java.util.List;

/**
 *
 *
 */
public interface CSV_Interface {

	public List<List<String>> importCSV(String filename);

	public void exportCSV(List<User> users, List<Profile> profiles, List<Product> products, List<Category> cathegory);

}
