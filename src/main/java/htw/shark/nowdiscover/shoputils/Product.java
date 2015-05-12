package htw.shark.nowdiscover.shoputils;

import java.util.List;

public interface Product extends ShopConcept {

	void addCategories(List<Category> categories) throws ShopException;

	List<Category> getCategories();

	void removeCategories(List<Category> categories) throws ShopException;

	void addRelatedProducts(List<Product> products) throws ShopException;

	List<Product> getRelatedProducts();

	void removeRelatedProducts(List<Product> products) throws ShopException;

	List<Product> getRecommendations();

}
