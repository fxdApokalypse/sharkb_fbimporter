package net.sharkfw.apps.fb.util;

import net.sharkfw.knowledgeBase.*;
import org.springframework.social.facebook.api.Reference;


/**
 * Helper Methods for handling {@link SharkKB} tasks.
 */
public class SharkUtil  {

    /**
     * <p>Retrieves a PeerSemanticTag for a specific FBUser by a User Reference
     * or create one for the corresponding user. The reference
     * id is converted to SI and is used for querying the {@link SharkKB} for the
     * PeerSemanticTag</p>
     *
     * @param ref the reference for corresponding user.
     * @param kb the underlying shark kb.
     * @return the corresponding {@link PeerSNSemanticTag} for the requested user.
     * @throws SharkKBException if there was an error accessing the SharkKB.
     */
    public static PeerSNSemanticTag getPeerSemanticTagBy(Reference ref, SharkKB kb) throws SharkKBException {

        String si = FacebookUtil.createUserLink(ref.getId());
        PeerSemanticNet psn = kb.getPeersAsSemanticNet();
        PeerSNSemanticTag userPT = psn.getSemanticTag(si);
        if (userPT == null) {
            userPT = psn.createSemanticTag(ref.getName(), si, (String) null);
        }
        return userPT;
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
