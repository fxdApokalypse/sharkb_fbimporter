package htw.shark.nowdiscover.shoputils;

import net.sharkfw.knowledgeBase.*;

/**
 * 
 *
 */
public interface Category extends ShopConcept {

	void setName(String name);

	void addUrls(String... urls) throws SharkKBException;

	void removeUrls(String urls[]) throws SharkKBException;

}
