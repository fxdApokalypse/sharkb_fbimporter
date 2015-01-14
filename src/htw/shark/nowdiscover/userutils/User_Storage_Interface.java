/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

import htw.shark.nowdiscover.*;
import net.sharkfw.knowledgeBase.*;

/**
 *
 * @author s0542818
 */
public interface User_Storage_Interface {

	void createUser(String name, String sui) throws SharkKBException;

	void removeUser(User user);

	User getUser();

	void createProfile(User user);

	Profile getProfile();

	void removeProfile(Profile profile);

	void createInterest(ShopConcept shopConcept, Profile profile,
			String property, String value);

	void removeInterest(Interest interest);

	Interest getInterest();
}
