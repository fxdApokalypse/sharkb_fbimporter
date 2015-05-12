package htw.shark.nowdiscover.shoputils;

import java.util.Arrays;

import net.sharkfw.knowledgeBase.SNSemanticTag;
import net.sharkfw.knowledgeBase.SemanticNet;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;

public abstract class AbstractShopConcept implements ShopConcept {

	private static final int	MAX_NAME_LENGTH	= 20;

	private static final int	MIN_NAME_LENGTH	= 2;

	protected SharkKB	     kb;

	protected SNSemanticTag	 tag;

	protected AbstractShopConcept(SharkKB kb, String name, String... urls) throws SharkKBException {
		verifyName(name);
		this.kb = kb;

		SemanticNet sn = kb.getTopicsAsSemanticNet();

		this.tag = sn.getSemanticTag(urls);

		if (tag == null) {// gibt es null zurueck, wenn es den Tag nicht gibt?
			tag = sn.createSemanticTag(name, urls);
		} else {
			tag.setName(name);
		}

	}

	@Override
	public void setName(String name) {
		verifyName(name);
		this.tag.setName(name);
	}

	@Override
	public String getName() {
		return this.tag.getName();
	}

	@Override
	public String[] getUrls() {
		return this.tag.getSI();
	}

	@Override
	public void addUrls(String... urls) throws SharkKBException {
		for (String url : this.tag.getSI()) {
			for (String newUrl : urls) {
				if (!url.startsWith(newUrl)) {
					this.tag.addSI(newUrl);
				}
			}
		}
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

	public void verifyName(String name) {
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Name must be longer than " + MIN_NAME_LENGTH
			        + "  and shorter than " + MAX_NAME_LENGTH + " chars.");
		}
	}

	public String toString() {
		return tag.getName() + " " + Arrays.asList(tag.getSI());
	}

}
