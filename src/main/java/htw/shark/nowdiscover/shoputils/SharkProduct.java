package htw.shark.nowdiscover.shoputils;

import htw.shark.nowdiscover.factory.ShopEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import net.sharkfw.knowledgeBase.SNSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;

public class SharkProduct extends AbstractShopConcept implements Product {

	private static final String	IS_RELATED_TO	= "isRelatedTo";

	private static final String	BELONGS_TO	  = "belongsTo";

	public SharkProduct(SharkKB kb, String name, String... urls) throws SharkKBException {
		super(kb, name, urls);
	}

	public void addRelatedProducts(List<Product> prods) {
		for (Product product : prods) {
			if (product instanceof SharkProduct) {
				SharkProduct sProd = (SharkProduct) product;
				this.tag.setPredicate(IS_RELATED_TO, sProd.tag);
			} else {
				System.out.println("Something is wrong with your products!");
			}
		}
	}

	@Override
	public List<Product> getRelatedProducts() {
		List<Product> pList = new ArrayList<>();
		// get all related tags
		Enumeration<SNSemanticTag> enumTags = this.tag.targetTags(SharkProduct.IS_RELATED_TO);

		while (enumTags != null && enumTags.hasMoreElements()) {
			SNSemanticTag aTag = enumTags.nextElement();
			try {
				pList.add(ShopEngine.getShopEngine().createProduct(aTag.getName(), aTag.getSI()));
			} catch (ShopException e) {
				e.printStackTrace();
			}
		}
		// sort alphabetically
		Collections.sort(pList, new Comparator<Product>() {

			@Override
			public int compare(Product p1, Product p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		return pList;
	}

	@Override
	public void addCategories(List<Category> categories) throws ShopException {

		for (Category category : categories) {
			if (category instanceof SharkCategory) {
				SharkCategory sCat = (SharkCategory) category;
				this.tag.setPredicate(BELONGS_TO, sCat.tag);
			}
		}

	}

	@Override
	public List<Category> getCategories() {
		List<Category> pList = new ArrayList<>();
		// get all related tags
		Enumeration<SNSemanticTag> enumTags = this.tag.targetTags(SharkProduct.BELONGS_TO);
		while (enumTags != null && enumTags.hasMoreElements()) {
			SNSemanticTag aTag = enumTags.nextElement();
			Category c = null;
			try {
				c = new SharkCategory(kb, aTag.getName(), aTag.getSI());
			} catch (SharkKBException e) {
				e.printStackTrace();
			}
			pList.add(c);
		}
		// sort alphabetically
		Collections.sort(pList, new Comparator<Category>() {

			@Override
			public int compare(Category c1, Category c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
		return pList;
	}

	@Override
	public void removeCategories(List<Category> categories) throws ShopException {
		try {
			List<String> toRemove = new ArrayList<String>();
			// find all related categories
			Enumeration<SNSemanticTag> enumTags = this.tag.targetTags(SharkProduct.BELONGS_TO);
			while (enumTags != null && enumTags.hasMoreElements()) {
				SNSemanticTag aTag = enumTags.nextElement();
				for (Category category : categories) {
					if (aTag.getName().equals(category.getName())) {
						for (String si : aTag.getSI()) {
							toRemove.add(si);
						}
					}
				}
			}
			String toRemoveArray[] = toRemove.toArray(new String[toRemove.size()]);
			kb.removeSemanticTag(toRemoveArray);
		} catch (SharkKBException e) {
			throw new ShopException(e);
		}
	}

	@Override
	public void removeRelatedProducts(List<Product> products) throws ShopException {
		List<String> toRemove;
		String[] toRemoveArray;
		try {
			toRemove = new ArrayList<>();
			// find all related products
			Enumeration<SNSemanticTag> enumTags = this.tag.targetTags(SharkProduct.IS_RELATED_TO);
			while (enumTags != null && enumTags.hasMoreElements()) {
				SNSemanticTag aTag = enumTags.nextElement();
				for (Product product : products) {
					if (aTag.getName().equals(product.getName())) {
						for (String si : aTag.getSI()) {
							toRemove.add(si);
						}
						toRemoveArray = toRemove.toArray(new String[toRemove.size()]);
						kb.removeSemanticTag(toRemoveArray);
					}
				}
			}

		} catch (SharkKBException e) {
			throw new ShopException(e);
		}
	}

	/**
	 * find the product's categories -> return all the other products which
	 * belong to these categories.
	 */
	@Override
	public List<Product> getRecommendations() {
		List<Product> recommended = new ArrayList<>();
		try {
			Product tmp;
			Enumeration<SNSemanticTag> categories = this.tag.targetTags(BELONGS_TO);
			while (categories != null && categories.hasMoreElements()) {
				SNSemanticTag catTag = categories.nextElement();
				Enumeration<SNSemanticTag> productsOfCategory = catTag.sourceTags(BELONGS_TO);
				while (productsOfCategory != null && productsOfCategory.hasMoreElements()) {
					SNSemanticTag aTag = productsOfCategory.nextElement();
					if (aTag != this.tag) {
						tmp = new SharkProduct(kb, aTag.getName(), aTag.getSI());
						recommended.add(tmp);
					}
				}
			}

		} catch (SharkKBException e) {
			e.printStackTrace();
		}
		// sort alphabetically
		Collections.sort(recommended, new Comparator<Product>() {

			@Override
			public int compare(Product p1, Product p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		return recommended;
	}
}
