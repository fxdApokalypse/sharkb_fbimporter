package htw.shark.nowdiscover;

import htw.shark.nowdiscover.userutils.*;
import net.sharkfw.system.*;

public interface Shop {
	public Category createCategory(String name, String url)
			throws shopException, SharkException;

	public Category createCategory(String name, String... url)
			throws shopException, SharkException;

	public Product createProduct(String name);

	public User createUser(String name, String url);
	// remove..
	// get..

}
