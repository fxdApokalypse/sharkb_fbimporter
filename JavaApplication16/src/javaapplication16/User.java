/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication16;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.ContextSpace;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SNSemanticTag;
import net.sharkfw.knowledgeBase.STSet;
import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkCS;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.SharkEngine;

/**
 *
 * @author Jörn
 */
public class User implements User_Interface{
  private PeerSemanticTag user;
  STSet InterestsTags = InMemoSharkKB.createInMemoSTSet();
  private ArrayList<Interest> Interets;
  private ArrayList<User> users = UserStorage.getInstance().getUserList();
 
  

//enum that will be later used (for testing purposes) to evaluate the users Interests
 
  public enum like {
    HIGH(1),
    MEDIUM(0),
    LOW(-1);
    
    int value;
    
    like(int value){
    this.value = value;
    }
}
  
  //Constructor for the User Class requires a PST
   public User(String name, String sui) throws SharkKBException{
   user.addSI(sui);
   user.setName(name);
   

   }
   
   private PeerSemanticTag getPeerSemanticTag(){
   return user;
   }

 
  @Override
    public void updateUserInformation(PeerSemanticTag update) {
       this.user = update;
    }

    @Override
    public ArrayList<User> getUsers() {
       return UserStorage.getInstance().getUserList();
    }

    //meby~~ TODO Merge USER in PTSET of peers
    @Override
    public User getUser() {
        return User.this;
    }

    @Override
    public void addInterest(Interest interest)  throws SharkKBException { 
          Interets.add(interest);
          InterestsTags.merge(interest.getInterest());     
    }

    
    //Allows to Evaluate a Users Interest
    @Override
    public void evaluateInformation(Interest interest,String value) {
        interest.getInterest().setProperty(value, null);
    }

    @Override
    public ArrayList<Interest> getUserInterets() {
        return Interets;
    }
    
    //TODO Vergleich von usern über Shark Dev Guide S 55

    @Override
    public ContextPoint interesttocontext(SNSemanticTag interestTag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   
   



    
}
