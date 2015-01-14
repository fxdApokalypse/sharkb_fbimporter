
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

/**
 *
 * @author Holland, Sattler
 */

import net.sharkfw.knowledgeBase.*;

public class User {
	// requires userid v
	// create pst v
	// fill with subject identifier v
	// Methode: return PST v
	// Methode: delete PST
	// Methoden zum updaten der Inhalte des PST

	// WAS FEHLT: TagSet, Knowledgebase, da die meiner Meinung nach au�erhalb
	// der Klasse erzeugt werden m�ssen
	// was die Klasse hier returned muss dann in sets und KB geadded werden
	// und kann da auch wieder rausgeschmissen werden

	// any tag
	private PeerSemanticTag user;

	// Constructor for the User Class requires a PST
	public User(String userid, String sui) throws SharkKBException {
		user.addSI(sui); // string muss �bergeben werden, stringarray wird dann
							// wohl innerhalb des PST angelegt
		user.setName(userid);
	}

	// Methoden zum �ndern der Inhalte des PST
	public void setNewSI(PeerSemanticTag user, String sui)
			throws SharkKBException {
		user.addSI(sui);
	}

	void removeSI(PeerSemanticTag user) throws SharkKBException {
		user.removeSI(null);
	}

	void setNewName(PeerSemanticTag user, String name) throws SharkKBException {
		user.setName(name);
	}

	// PST wird returned an Controller(Interface) und geht an Constructor von
	// UserProfile
	private PeerSemanticTag getPeerSemanticTag() {
		return user;
	}

	// L�schen: In Java werden Objekte nicht gel�scht. Sobald es keine Verweise
	// mehr auf das Objekt gibt
	// weil es zum Beispiel aus allen Sets und KBs entfernt wurde, l�scht es der
	// Garbage Collector selbst
	/*
	 * void removePeerSemanticTag () {
	 * 
	 * 
	 * }
	 */
}
