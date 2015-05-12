package htw.shark.nowdiscover.shoputils;

import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * 
 *
 */
public class SharkCategory extends AbstractShopConcept implements Category {

	public SharkCategory(SharkKB kb, String name, String... urls) throws SharkKBException {
		super(kb, name, urls);
	}

}
