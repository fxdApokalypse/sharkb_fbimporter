package htw.shark.nowdiscover.userutils;

import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkCS;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * Class representing an Interest
 * 
 * @author Jörn Sattler s0542818
 */

public class Interest implements Interest_Interface {

	ContextPoint	cp;

	/**
	 * Konstruktor
	 * 
	 * @param shopConcept
	 *            Requires a Product or a Product Category
	 * @param user
	 *            Requires a peer Semantic Tag (you won't be using this Konstruktor anyway)
	 * @param rating
	 *            a rating describing how much the user is liking or disliking the interest, we currently use a String
	 * @param kb
	 *            Shark Knowledge base (you wont be seeing this anyway)
	 * @throws SharkKBException
	 */
	public Interest(SemanticTag shopConcept, PeerSemanticTag user, String rating, SharkKB kb)
	        throws SharkKBException {

		cp =
		        kb.createContextPoint(kb.createContextCoordinates(shopConcept, user, null, null,
		                null, null, SharkCS.DIRECTION_INOUT));
		cp.setProperty("rating", rating);

	}

	public Interest(SemanticTag shopConcept, PeerSemanticTag user, SharkKB kb)
	        throws SharkKBException {
		cp =
		        kb.createContextPoint(kb.createContextCoordinates(shopConcept, user, null, null,
		                null, null, SharkCS.DIRECTION_INOUT));
		cp.setProperty("rating", "0");

	}

	public Interest(ContextPoint cp) {
		this.cp = cp;
	}

	/**
	 * Returns the Interest Object
	 * 
	 * @return Object Interest
	 */
	@Override
	public Interest getInterest() {
		return Interest.this;
	}

	/**
	 * Returns the userID of the User the Interest belongs too
	 * 
	 * @return String: userID
	 */
	@Override
	public String getUserID() {

		return cp.getContextCoordinates().getOriginator().getName();
	}

	/**
	 * Returns the name of the topic the Interest is about
	 * 
	 * @return String: topicname
	 */
	@Override
	public String getTopicname() {
		return cp.getContextCoordinates().getTopic().getName();
	}

	/**
	 * Allows changing the rating of the Interest
	 * 
	 * @param rating
	 *            requires String : the new rating you want to set for this interest
	 * @return
	 */
	@Override
	public String changeRating(String rating) {
		cp.setProperty("rating", rating);
		return "New Rating: " + cp.getProperty(rating);
	}

	/**
	 * removes the current Rating and sets the rating to default "0"
	 */
	@Override
	public void RemoveRating() {
		cp.setProperty("rating", "0");
	}

	/**
	 * 
	 * @return the rating of an interest
	 */
	@Override
	public String getRating() {
		return cp.getProperty("rating");
	}

}
