package net.sharkfw.apps.fb.util.shark;

import net.sharkfw.apps.fb.util.facebook.FacebookUtil;
import net.sharkfw.knowledgeBase.*;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;


public class KBUtils {

    /**
     * The si for the facebook user taxonomy which is
     * used to sign a peer tag as imported facebook user.
     */
    public static final String FACEBOOK_USERS_TAXONOMY_SI = "https://facebook.com/user";

    /**
     * The name for the facebook user taxonomy which is
     * used to sign a peer tag as imported facebook user.
     */
    public static final String FACEBOOK_USERS_TAXONOMY_NAME = "FACEBOOK_USERS";

    /**
     * The name of the {@link SNSemanticTag} predicate which
     * models the friendship relationship between {@link PeerSemanticTag}.
     */
    public static final String FRIENDSHIP_EDGE = "friendship";


    private KBUtils() {}

    /**
     * <p>Retrieves a PeerSNSemanticTag for a specific FBUser by a User Reference
     * or create one for the corresponding user. The reference
     * id is converted to SI and is used for querying the {@link SharkKB} for the
     * PeerSemanticTag</p>
     *
     * @param ref the reference for corresponding user.
     * @param kb the underlying shark kb.
     * @return the corresponding {@link PeerSNSemanticTag} for the requested user.
     * @throws SharkKBException if there was an error accessing the SharkKB.
     */
    public static PeerSNSemanticTag createPeerSNTagFrom(Reference ref, SharkKB kb) throws SharkKBException {
        String id = ref.getId();
        String si = FacebookUtil.createUserLink(id);
        String name = ref.getName();

        PeerSemanticNet peerSemanticNet = kb.getPeersAsSemanticNet();
        PeerSNSemanticTag userPeerSemanticTag = peerSemanticNet.getSemanticTag(si);
        if (userPeerSemanticTag == null) {
            userPeerSemanticTag = peerSemanticNet.createSemanticTag(ref.getName(), si, (String) null);
            signAsFacebookUser(userPeerSemanticTag, kb);
        }
        return userPeerSemanticTag;
    }

    /**
     * <p>Retrieves a PeerSNSemanticTag for a specific FBUser by a User object
     * or create one for the corresponding user. The object
     * id is converted to SI and is used for querying the {@link SharkKB} for the
     * PeerSemanticTag</p>
     *
     * @param user the object for corresponding user.
     * @param kb the underlying shark kb.
     * @return the corresponding {@link PeerSNSemanticTag} for the requested user.
     * @throws SharkKBException if there was an error accessing the SharkKB.
     */
    public static PeerSNSemanticTag createPeerSNTagFrom(User user, SharkKB kb) throws SharkKBException {
        PeerSNSemanticTag userPeerSemanticTag = createPeerSNTagFrom(new Reference(user.getId(), user.getName()), kb);
        SemanticTagUtils.fillStringProperties(userPeerSemanticTag, user);

        String email = user.getEmail();
        if (email != null) {
            userPeerSemanticTag.setAddresses(new String[] {email});
        }

        return userPeerSemanticTag;
    }

    /**
     * Sign the given PeerSemanticTag as FacebookUser this
     * means the given Tag is moved und the TaxonomyTag called Facebook user.
     *
     * @param peerSemanticTag the tag which should be signed
     * @param kb the underlying shark kb.
     * @throws SharkKBException if there was an error accessing the SharkKB.
     */
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

    /**
     * Connects PeerSNSemanticTag (alias as users) as friends.
     *
     * @param user a user
     * @param friends and it's friends.
     */
    public static void connectAsFriends(PeerSNSemanticTag user, PeerSNSemanticTag ... friends) {
        for (PeerSNSemanticTag friend : friends) {
            connectBidirectional(FRIENDSHIP_EDGE, user, friend);
        }
    }

    /**
     * Connects two SNSemanticTags by a named edged (set it's predicates).
     * The created edge is bidirectional so that connection goes
     * from the src tag to the destination tag and from the destination tag
     * to the source tag.
     *
     * @param connectionType The name of the connectionType e.g. "friends" and so on
     * @param src The tag which acts as source tag.
     * @param tgt The tag which acts as destination tag.
     */
    public static void connectBidirectional(String connectionType, SNSemanticTag src, SNSemanticTag tgt) {
        connect(connectionType, src, tgt, true);
    }

    /**
     * Connects two SNSemanticTags by a named edged (set it's predicates).
     * The created edge is only directional so that connection goes only
     * from the src tag to the destination tag.
     *
     * @param connectionType The name of the connectionType e.g. "friends" and so on
     * @param src The tag which acts as source tag.
     * @param tgt The tag which acts as destination tag.
     */
    public static void connectDirectional(String connectionType, SNSemanticTag src, SNSemanticTag tgt) {
        connect(connectionType, src, tgt, false);
    }

    /**
     * Connects two SNSemanticTags by a named edged (set it's predicates).
     * The connection could be bidirectional or directed
     * which depends on the flag called <code>biDirectional</code>.
     *
     *
     * @param connectionType The name of the connectionType e.g. "friends" and so on
     * @param src The tag which acts as source tag.
     * @param tgt The tag which acts as destination tag.
     * @param biDirectional if true the created connection is bidirectional otherwise directional
     */
    public static void connect(String connectionType, SNSemanticTag src, SNSemanticTag tgt, boolean biDirectional) {
        src.setPredicate(connectionType, tgt);
        if (biDirectional) {
            tgt.setPredicate(connectionType, src);
        }
    }

    /**
     * Converts a PeerSemanticTag to a PeerSNSemanticTag.
     *
     * @param pst the to be converted PeerSemanticTag
     * @param kb the underlying sharkKB
     * @return the converted {@link PeerSNSemanticTag}
     * @throws SharkKBException if there was an error converting the PeerSemanticTag.
     */
    public static PeerSNSemanticTag getPeerSemantigTagAsPeerSNSemanticTag(PeerSemanticTag pst, SharkKB kb ) throws SharkKBException {
        PeerSemanticNet psn = kb.getPeersAsSemanticNet();
        return psn.getSemanticTag(pst.getSI());
    }
}
