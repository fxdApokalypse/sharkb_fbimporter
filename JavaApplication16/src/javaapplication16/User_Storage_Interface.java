/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication16;

import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

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

    void createInterest(Thing thing, Profile profile, String property, String value);
    
    void removeInterest(Interest interest);

    Interest getInterest()
}
