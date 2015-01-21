/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

import htw.shark.nowdiscover.*;

import java.util.*;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;
import net.sharkfw.system.*;

/**
 * The class is a factory for categories, products, users, profiles and
 * interests and has a Singelton KnowledgedeBase.
 * 
 * @author Jörn
 */
public class ShopEngine implements Shop {

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
	public Category createCategory(String name, String url)
			throws shopException, SharkException {
		Category category = new SharkCategory();
		category.setName(name);
		category.addUrls(url);
		return category;
	}

	@Override
	public Category createCategory(String name, String... url)
			throws shopException, SharkKBException {
		Category category = new SharkCategory();
		category.setName(name);
		category.addUrls(url);
		return category;
	}

	@Override
	public Product createProduct(String name, String url) {
		Product product = new SharkProduct(name, url);
		return product;
	}

	@Override
	public Product createProduct(String name, String... url) {
		Product product = new SharkProduct(name, url);
		return product;
	}

	@Override
	public User createUser(String name, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Profile> getProfiles() throws SharkKBException {

		// shop engine holen
		Knowledge knowledge = SharkCSAlgebra.extract(kb, null);
		Enumeration<ContextPoint> contextPoints = knowledge.contextPoints();
		if (contextPoints != null) {
			while (contextPoints.hasMoreElements()) {
				ContextPoint nextElement = contextPoints.nextElement();
				Interest interest = new Interest(nextElement);
			}
		}
		return null;

	}

	@Override
	public void createProfile(User user) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public Profile getProfile() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void removeProfile(Profile profile) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void createInterest(ShopConcept shopConcept, Profile profile,
			String property, String value) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void removeInterest(htw.shark.nowdiscover.userutils.Interest interest) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public htw.shark.nowdiscover.userutils.Interest getInterest() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

}
