package htw.shark.nowdiscover;

import net.sharkfw.knowledgeBase.*;

/**
 * Work in Progress!!!
 *
 */
public interface Category extends ShopConcept {

	void setName(String name);

	void addUrls(String urls[]) throws SharkKBException;

	void removeUrls(String urls[]) throws SharkKBException;

	String getCategoryName();

	String[] getUrls();

}
