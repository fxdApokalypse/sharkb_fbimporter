/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication16;

/**
 *
 * @author Jörn
 */
public interface KnowledgeGoddesss_Interface {

    public void storeKnowledge(Thing thing);

    public void storeKnowledge(Profile profile);

    public void storeKnowledge(User user);

    public void storeKnowledge(Product product);

    public void storeKnowledge(Interest interest);

    public void storeKnowledge(Cathegory cathegory);
    
    public void removeKnowledge(Thing thing);

    public void removeKnowledge(Profile profile);

    public void removeKnowledge(User user);

    public void removeKnowledge(Product product);

    public void removeKnowledge(Interest interest);

    public void removeKnowledge(Cathegory cathegory);
    

}
