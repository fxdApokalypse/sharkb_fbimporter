package htw.shark.nowdiscover.userutils;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jörn, Claus, Alex - implments the User Profile (Peer-Semantic Tag +
 *         Context Points)
 */

// Anforderungen: v
// UserProfile wird vom Interface erzeugt, übergeben wird ein PST aus einer
// User-Instanz v
// Übergeben wird ein User Objekt v
// CPs v
// Methoden: die Argumente enthalten aus der Produktgruppe
// Es fehlt Liste aus CPs
// Es fehlt remove CP, change CC

public class Profile {

	private PeerSemanticTag user;

	// Constructor for the UserProfile Class requires a PST (requires a
	// controller)
	public Profile(PeerSemanticTag user) {
		this.user = user;

	}

	// Methoden zur Erzugung und Veränderung von ContextPunkten

	InMemoSharkKB kb = new InMemoSharkKB();

	ContextCoordinates cc = InMemoSharkKB.createInMemoContextCoordinates(null, // topic
			null, // originator
			user, // peer
			null, // remote peer : any
			null, // time: any
			null, // location: any
			SharkCS.DIRECTION_OUT); // direction

	ContextPoint createContextPoint() throws SharkKBException {
		ContextPoint kbCP = kb.createContextPoint(cc);
		return kbCP;
	}

	// Rückgabe der Contextpunkte in Arrayform eines Users
	public ContextPoint getCPs() throws SharkKBException {
		return createContextPoint();
	}

}
