package htw.shark.nowdiscover;

import java.util.*;

import net.sharkfw.knowledgeBase.*;

/**
 * 
 *
 */
public interface Product extends ShopConcept {
	// define the product's categories
	void addCategories(List<Category> categories);

	// returns all product categories
	List<Category> getCategories();

	// define related products
	void addRelatedProducts(List<Product> products) throws SharkKBException;

	// get the related products
	List<Product> getRelatedProducts();

}
