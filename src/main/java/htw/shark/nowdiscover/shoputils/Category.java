package htw.shark.nowdiscover.shoputils;

import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * 
 *
 */
public interface Category extends ShopConcept {

	void setName(String name);

	public String getName();

	public String[] getUrls();

	void addUrls(String... urls) throws SharkKBException;

	void removeUrls(String urls[]) throws SharkKBException;

}
