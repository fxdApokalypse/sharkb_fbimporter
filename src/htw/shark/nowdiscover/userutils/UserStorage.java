package htw.shark.nowdiscover.userutils;

import htw.shark.nowdiscover.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

/**
 *
 * @author Jörn Sattler s0542818 , Alexander Ihm s0543565 /**
 */
public final class UserStorage implements User_Storage_Interface {
	SharkKB kb = new InMemoSharkKB();

	@Override
	// TODO USER NEEDS METHODS TO CHANNGE / ADD SUI NAME AND ADRESSES ALSO ADD
	// DEFAULT CONSTRUVTOR TO USER WITHOUT ARGUMENTS
	public void createUser(String name, String sui) throws SharkKBException {

		User user = new User(name, sui);

	}

	@Override
	public void removeUser(User user) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	// @Override
	// public User getUser(String name) {
	// User user = kb.getSemanticTag(name)
	//
	// return User user;
	// }

	@Override
	public void createProfile(User user) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public Profile getProfile() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void removeProfile(Profile profile) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void createInterest(ShopConcept shopConcept, Profile profile,
			String property, String value) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void removeInterest(Interest interest) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public Interest getInterest() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public void createInterest(ShopConcept shopConcept, Profile profile,
	// String property, String value) {
	// // TODO Auto-generated method stub
	//
	// }

}
