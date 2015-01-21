package htw.shark.nowdiscover;

import htw.shark.nowdiscover.userutils.*;
import net.sharkfw.knowledgeBase.*;

/**
 * 
 *
 */
public class SharkCategory implements Category {
	SemanticTag tag = null;

	public SharkCategory(String name, String... url) throws SharkKBException {
		tag = ShopEngine.getKB().createSemanticTag(name, url);
	}

	public SharkCategory(SemanticTag tag) throws SharkKBException {
		this.tag = tag;
	}

	public SharkCategory() {

	}

	@Override
	public void setName(String name) {
		this.tag.setName(name);
	}

	@Override
	public void addUrls(String[] urls) throws SharkKBException {
		for (String url : urls) {
			this.tag.addSI(url);
		}
	}

	@Override
	public String getCategoryName() {
		return this.tag.getName();
	}

	@Override
	public String[] getUrls() {
		return tag.getSI();
	}

	@Override
	public void removeUrls(String[] urls) throws SharkKBException {
		if (tag.getSI() != null) {
			for (String url : urls) {
				for (String si : tag.getSI()) {
					if (url.equals(si)) {
						tag.removeSI(si);
						break;
					}
				}
			}
		}

	}

}
