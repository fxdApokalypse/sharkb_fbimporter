/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication16;

import java.util.ArrayList;
import net.sharkfw.knowledgeBase.SNSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;

/**
 *
 * @author Jörn
 */
public interface Interest_Interface {
        void setUsername(String name);
        
        void setUserinfomation(String info[])throws SharkKBException;
    
        //defines the Interests name
    	void setName(String name);
        //defines the Sui of the interest
	void setUrls(String urls[]) throws SharkKBException;
        
        void setProperty_Value(String Property,String Value);
        
        String getUsername();
        
        String[] getUserinformation();
    
        String getName();
        
        String[] getUrls();
        
        String getProperty();
        
        String getValue();
            
        void remove_userinformation() throws SharkKBException;
             
        void removeUrls()  throws SharkKBException;
        
        void removeProperty_Value();
        
        Interest getInterest();
        
       
}


    

