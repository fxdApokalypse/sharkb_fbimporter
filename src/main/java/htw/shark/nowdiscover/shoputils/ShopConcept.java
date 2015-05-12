package htw.shark.nowdiscover.shoputils;

import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * This interface is being extended by the Product and Category interfaces und
 * contains the methods they share.
 *
 */
public interface ShopConcept {

	void setName(String name);

	String getName();

	String[] getUrls();

	void addUrls(String... urls) throws SharkKBException;

	void removeUrls(String urls[]) throws SharkKBException;
}
