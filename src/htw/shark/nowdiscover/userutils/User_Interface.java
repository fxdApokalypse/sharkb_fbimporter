/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package htw.shark.nowdiscover.userutils;

import net.sharkfw.knowledgeBase.*;

/**
 *
 * @author Jörn
 */
public interface User_Interface {

	public User getUser();

	public String getName();

	public void setName(String name);

	public void setSI(String... suis) throws SharkKBException;

	// public void setSUI(String suis) throws SharkKBException;

	public String[] getSUI();

}
