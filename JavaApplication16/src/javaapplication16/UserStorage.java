

package javaapplication16;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

/**
 *
 * @author Jörn Sattler s0542818  , Alexander Ihm s0543565  
/**
 */
public final class UserStorage implements User_Storage_Interface {
SharkKB kb = new InMemoSharkKB();
    
    @Override
    //TODO USER NEEDS METHODS TO CHANNGE / ADD SUI NAME AND ADRESSES ALSO ADD DEFAULT CONSTRUVTOR TO USER WITHOUT ARGUMENTS
    public void createUser(String name, String sui) throws SharkKBException  {
   
        User user = new User(name, sui);
 
    }

    @Override
    public void removeUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(String name) {
        kb.getSemanticTag(name)
        
        return User user;
    }

    @Override
    public void createProfile(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Profile getProfile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeProfile(Profile profile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createInterest(Thing thing, Profile profile, String property, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeInterest(Interest interest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interest getInterest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    


    
  
    
    }


