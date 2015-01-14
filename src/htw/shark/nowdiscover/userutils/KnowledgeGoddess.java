/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

import net.sharkfw.knowledgeBase.inmemory.*;

/**
 *
 * @author Jörn
 */
public class KnowledgeGoddess {
	// TODO eventuell listen von objekten
	private static KnowledgeGoddess instance = null;
	private static InMemoSharkKB kb;

	private KnowledgeGoddess() {
	}

	public static void initInstance() {
		if (instance == null) {
			instance = new KnowledgeGoddess();
		}

	}

	public static InMemoSharkKB getKB() {
		return kb;
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
