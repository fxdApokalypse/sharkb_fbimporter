/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package htw.shark.nowdiscover.userutils;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

/**
 *
 * @author Jörn
 */
public class Interest implements Interest_Interface {
	InMemoSharkKB kb = new InMemoSharkKB();

	private SemanticTag topic;
	private PeerSemanticTag user;
	private String name;
	private String property;
	private String value;

	public Interest() {
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setUrls(String[] urls) throws SharkKBException {
		for (String url : urls) {
			topic.addSI(url);
		}
	}

	@Override
	public void setProperty_Value(String property, String value) {
		this.property = property;
		this.value = value;
		topic.setProperty(property, value);
	}

	@Override
	public void setUsername(String name) {
		user.setName(name);
	}

	@Override
	public void setUserinfomation(String[] info) throws SharkKBException {
		for (String infos : info) {
			user.addSI(infos);
		}
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public String[] getUserinformation() {
		return user.getSI();
	}

	@Override
	public String getName() {
		return topic.getName();
	}

	@Override
	public String[] getUrls() {
		return topic.getSI();
	}

	@Override
	public String getProperty() {
		return property;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void remove_userinformation() throws SharkKBException {
		for (String userinfo : getUserinformation()) {
			user.removeSI(userinfo);
		}
	}

	@Override
	public void removeUrls() throws SharkKBException {
		for (String url : getUrls()) {
			topic.removeSI(url);
		}
	}

	@Override
	public void removeProperty_Value() {
		topic.setProperty(name, null);
		name = null;
		value = null;

	}

	// Prototype
	public void createInterest() throws SharkKBException {
		ContextCoordinates cc = kb.createContextCoordinates(topic, user, user,
				null, null, null, SharkCS.DIRECTION_NOTHING);
		kb.createContextPoint(cc);

	}

	@Override
	public Interest getInterest() {
		return Interest.this;
	}

}