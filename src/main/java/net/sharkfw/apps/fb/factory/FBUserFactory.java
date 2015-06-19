package net.sharkfw.apps.fb.factory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

import net.sharkfw.knowledgeBase.FragmentationParameter;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import net.sharkfw.knowledgeBase.PeerSemanticNet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.system.L;

import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;


public class FBUserFactory {

	private static final String FACEBOOK_BASE_URL = "http://facebook.com/";
	private static final String IN_A_RELATIONSHIP = "inARelationship";
	private static final String IN_A_FRIENDSHIP = "inAFriendship";
	private static final String GETTER_PREFFIX = "get";

	public static FBUserFactory create(User user, SharkKB sharkKB) throws SharkKBException {
		if ( Objects.isNull(user)  || Objects.isNull(sharkKB)) {
			throw new IllegalArgumentException("Please provide a user instance.");
		}
	
		PeerSNSemanticTag me = getPeerSemanticTagOrCreateOne(user, sharkKB);
		System.out.println(user.getId());
		// fillPropertyHolder(user, peerSemanticTag);

		NamedFacebookType  significantOther = user.getSignificantOther();		
		if (significantOther != null) {
			User significantOtherUser = new User();
			significantOtherUser.setId(significantOther.getId());
			significantOtherUser.setName(significantOtherUser.getName());
			significantOtherUser.setLink(FACEBOOK_BASE_URL + significantOther.getId());
			
			PeerSNSemanticTag partner = getPeerSemanticTagOrCreateOne(significantOtherUser, sharkKB);
			me.setPredicate(IN_A_RELATIONSHIP, partner);
			partner.setPredicate(IN_A_RELATIONSHIP, me);	
			
			me.setPredicate(IN_A_RELATIONSHIP, partner);
			partner.setPredicate(IN_A_RELATIONSHIP, me);	
		}
		
		Vector<String> allowedPredicates = new Vector<String>(Arrays.asList(IN_A_RELATIONSHIP));
		Vector<String> forbiddenPredicates = new Vector<String>(Arrays.asList());
		FragmentationParameter fp = 
				new FragmentationParameter(allowedPredicates, forbiddenPredicates, 3);



		PeerSemanticNet semanticNet = sharkKB.getPeersAsSemanticNet();
		System.out.println(semanticNet.size());
		PeerSemanticNet fragment = semanticNet.fragment(me, fp);
		System.out.println("fragment:" + L.stSet2String(fragment));

		return null;
		
	}
	
	private static PeerSNSemanticTag getPeerSemanticTagOrCreateOne(User user, SharkKB sharkKB) throws SharkKBException {
		String si = user.getLink();
		String name = user.getName();
		String email = user.getEmail();
		
		PeerSemanticNet peerSemanticNet = sharkKB.getPeersAsSemanticNet();
		PeerSNSemanticTag peerSemanticTag = null;
		peerSemanticTag = peerSemanticNet.getSemanticTag(si);
		
		if (peerSemanticTag == null) {
			peerSemanticTag = peerSemanticNet.createSemanticTag(name, si, email);
		} 
		
		return peerSemanticTag;
	}

	private static void fillPropertyHolder(User user,
			PeerSemanticTag peerSemanticTag) {
		Class<?>  userClass =  user.getClass();
		Arrays.stream(userClass.getMethods())
		.filter((method) -> { 
			String methodName = method.getName();
			return methodName.startsWith(GETTER_PREFFIX)
				&& method.getParameterCount() == 0
				&& method.getReturnType() != Void.class;
		}).forEach((method) -> {
			String propertyName = method.getName().substring(3);
			try {
				String propertyValue = (String) method.invoke(user);					
				peerSemanticTag.setProperty(propertyName, propertyValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
