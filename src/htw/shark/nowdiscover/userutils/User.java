/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

/**
 *
 * @author Holland, Sattler
 */
import net.sharkfw.knowledgeBase.*;

public class User implements User_Interface {

	private PeerSemanticTag user;

	public User(String name, String sui) throws SharkKBException {
		user.addSI(sui);
		user.setName(name);
	}

	public User(String name) throws SharkKBException {
		user.setName(name);
	}

	public User() {
	}

	@Override
	public User getUser() {
		return User.this;
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public void setName(String name) {
		user.setName(name);
	}

	@Override
	public void setSI(String... suis) throws SharkKBException {
		for (String sui : suis) {
			user.addSI(sui);
		}
	}

	// @Override
	// public void setSUI(String suis) throws SharkKBException {
	// user.addSI(suis);
	// }

	@Override
	public String[] getSUI() {
		return user.getSI();
	}
}
