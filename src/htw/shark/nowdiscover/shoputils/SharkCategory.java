package htw.shark.nowdiscover.shoputils;

import net.sharkfw.knowledgeBase.*;

/**
 * 
 *
 */
public class SharkCategory implements Category {
	private static final int MAX_CATEGORY_NAME_LENGTH = 20;
	private static final int MIN_CATEGORY_NAME_LENGTH = 2;
	SemanticTag categoryTag = null;

	public SharkCategory(SharkKB kb, String name, String... urls)
			throws SharkKBException {
		NameExceptions(name);
		categoryTag = kb.getTopicSTSet().createSemanticTag(name, urls);
	}

	// public SharkCategory(SharkKB kb, SemanticTag tag) throws SharkKBException
	// {
	// this.categoryTag = tag;
	// }

	@Override
	public void setName(String name) {
		NameExceptions(name);
		this.categoryTag.setName(name);
	}

	public void NameExceptions(String name) {
		if (name.length() < MIN_CATEGORY_NAME_LENGTH
				|| name.length() > MAX_CATEGORY_NAME_LENGTH) {
			throw new IllegalArgumentException(
					"Category name must be longer than "
							+ MIN_CATEGORY_NAME_LENGTH + "  and shorter than "
							+ MAX_CATEGORY_NAME_LENGTH + " chars.");
		}
	}

	@Override
	public void addUrls(String[] urls) throws SharkKBException {
		for (String url : urls) {
			this.categoryTag.addSI(url);
		}
	}

	@Override
	public String getName() {
		return this.categoryTag.getName();
	}

	@Override
	public String[] getUrls() {
		return categoryTag.getSI();
	}

	@Override
	public void removeUrls(String[] urls) throws SharkKBException {
		if (categoryTag.getSI() != null) {
			for (String url : urls) {
				for (String si : categoryTag.getSI()) {
					if (url.equals(si)) {
						categoryTag.removeSI(si);
						break;
					}
				}
			}
		}

	}

}
