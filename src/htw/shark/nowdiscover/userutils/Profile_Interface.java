/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sharknowdiscover.htw.shark.nowdiscover.userutils;

import java.util.ArrayList;

/**
 *
 * @author draklor
 */
public interface Profile_Interface {
    
    public void addInterest(Interest interest);
    
    public void addInterest(ArrayList<Interest> Interests);
    
    public void removeInterest(Interest interest);
    
    
}
