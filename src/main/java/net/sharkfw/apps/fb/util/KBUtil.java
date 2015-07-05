package net.sharkfw.apps.fb.util;

import net.sharkfw.knowledgeBase.*;
import org.springframework.social.facebook.api.User;

public class KBUtil {

    public static final String FACEBOOK_USER_MARK = "is_facebook_user";
    public static final String FACEBOOK_USERS_TAXONOMY_SI = "https://facebook.com/users";
    public static final String FACEBOOK_USERS_TAXONOMY_NAME = "FACEBOOK_USERS";

    private KBUtil() {}


    public static PeerSNSemanticTag createPeerSNTagFrom(User user, SharkKB kb) throws SharkKBException {
        String id = user.getId();
        String si = FacebookUtil.createUserLink(id);
        String name = user.getName();
        String email = user.getEmail();

        PeerSemanticNet peerSemanticNet = kb.getPeersAsSemanticNet();
        PeerSNSemanticTag peerSemanticTag = peerSemanticNet.getSemanticTag(si);

        if (peerSemanticTag == null) {
            peerSemanticTag = peerSemanticNet.createSemanticTag(name, si, email);
            peerSemanticTag.setProperty(FACEBOOK_USER_MARK, "true");

            signAsFacebookUser(peerSemanticTag, kb);
        }

        SharkUtil.fillStringProperties(peerSemanticTag, user);

        return peerSemanticTag;
    }

    public static void signAsFacebookUser(PeerSNSemanticTag peerSemanticTag, SharkKB kb) throws SharkKBException {

        if (peerSemanticTag.getSuperTag() != null) return;

        PeerTaxonomy peerTaxonomy = kb.getPeersAsTaxonomy();

        TXSemanticTag facebookUsers = peerTaxonomy.getSemanticTag(FACEBOOK_USERS_TAXONOMY_SI);

        if (facebookUsers == null) {
            facebookUsers = kb.getPeersAsTaxonomy().createPeerTXSemanticTag(
                FACEBOOK_USERS_TAXONOMY_NAME,
                FACEBOOK_USERS_TAXONOMY_SI,
                (String) null
            );
        }

        peerSemanticTag.move(facebookUsers);
    }
}
