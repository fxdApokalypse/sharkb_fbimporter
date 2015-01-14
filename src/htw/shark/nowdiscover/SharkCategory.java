package htw.shark.nowdiscover;

import htw.shark.nowdiscover.userutils.*;
import net.sharkfw.knowledgeBase.*;

/**
 * 
 *
 */
public class SharkCategory implements Category {
	public SharkCategory() throws SharkKBException {

	}

	SharkKB kb = ShopEngine.getKB();
	SemanticNet sn = kb.getTopicsAsSemanticNet();
	private SNSemanticTag category;

	public SNSemanticTag getCategory() {
		return category;
	}

	@Override
	public void setCategory(String name, String... urls) {
		try {
			this.category = sn.createSemanticTag(name, urls);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addUrls(String[] urls) throws SharkKBException {
		for (String url : urls) {
			this.category.addSI(url);
		}
	}

	@Override
	public String getCategoryName() {
		return this.category.getName().toString();
	}

	@Override
	public String[] getUrls() {
		return category.getSI();
	}

	@Override
	public void removeUrls(String[] urls) throws SharkKBException {
		if (category.getSI() != null) {
			for (String url : urls) {
				for (String si : category.getSI()) {
					if (url.equals(si)) {
						category.removeSI(si);
						break;
					}
				}
			}
		}

	}

}
