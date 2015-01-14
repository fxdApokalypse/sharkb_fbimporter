/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package htw.shark.nowdiscover.userutils;

import java.util.*;

import net.sharkfw.knowledgeBase.*;

/**
 *
 * @author Jörn
 */
public interface User_Interface {

	// alters a Users Profile
	void updateUserInformation(PeerSemanticTag update);

	// returns all existing users
	ArrayList<User> getUsers();

	// returns one user
	User getUser();

	// defines an Interest of one User
	void addInterest(Interest interest) throws SharkKBException;

	ArrayList<Interest> getUserInterets();

	// allows to weighten the users Interests
	void evaluateInformation(Interest interest, String value);

	// returns an Context point with information about an interest of an user
	ContextPoint interesttocontext(SNSemanticTag interestTag);

}
