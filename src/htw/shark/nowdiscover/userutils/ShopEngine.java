/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

import htw.shark.nowdiscover.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;
import net.sharkfw.system.*;

/**
 *
 * @author Jörn
 */
public class ShopEngine implements Shop {
	// TODO eventuell listen von objekten
	private static ShopEngine instance = null;
	private static InMemoSharkKB kb;

	private ShopEngine() {
	}

	public static Shop getShopEngine() {
		if (instance == null) {
			instance = new ShopEngine();
		}
		return instance;
	}

	public static InMemoSharkKB getKB() {
		return kb;
	}

	@Override
	public Category createCategory(String name, String... url)
			throws shopException, SharkKBException {
		Category cat = new SharkCategory();
		cat.setName(name);
		cat.addUrls(url);
		return cat;
	}

	// @Override
	// public Category createCategory(String name, String... url) {
	// return null;
	// }

	@Override
	public Product createProduct(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(String name, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category createCategory(String name, String url)
			throws shopException, SharkException {
		// TODO Auto-generated method stub
		return null;
	}

	// public void storeKnowledge(ShopConcept thing) {
	//
	// }
	//
	// public void storeKnowledge(Profile profile) {
	//
	// }
	//
	// public void storeKnowledge(User user) {
	//
	// }
	//
	// public void storeKnowledge(Product product) {
	//
	// }
	//
	// public void storeKnowledge(Interest interest) {
	//
	// }
	//
	// public void storeKnowledge(Category cathegory) {
	//
	// }

}
