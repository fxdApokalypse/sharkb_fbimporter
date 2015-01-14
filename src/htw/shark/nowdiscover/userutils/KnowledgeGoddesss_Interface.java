/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

import htw.shark.nowdiscover.*;

/**
 *
 * @author Jörn
 */
public interface KnowledgeGoddesss_Interface {

	public void storeKnowledge(ShopConcept shopConcept);

	public void storeKnowledge(Profile profile);

	public void storeKnowledge(User user);

	public void storeKnowledge(Product product);

	public void storeKnowledge(Interest interest);

	public void storeKnowledge(Category cathegory);

	public void removeKnowledge(ShopConcept shopConcept);

	public void removeKnowledge(Profile profile);

	public void removeKnowledge(User user);

	public void removeKnowledge(Product product);

	public void removeKnowledge(Interest interest);

	public void removeKnowledge(Category cathegory);

}
