package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.FacebookUtil;
import net.sharkfw.apps.fb.util.SharkUtil;
import net.sharkfw.knowledgeBase.PeerSemanticNet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Imports the user related to the specified authorized access token.
 */
@Component
public class CurrentUserImporter extends BaseFBImporter {

    public static final String FACEBOOK_USER_MARK = "is_facebook_user";

    private Logger LOG = LoggerFactory.getLogger(CurrentUserImporter.class);

    public void performImport() throws FBImportException {
        PeerSemanticTag userTag = null;
        UserOperations userOperations = getFacebookAPI().userOperations();
        User user = userOperations.getUserProfile();

        try {
            userTag = getPeerSemanticTagOrCreateOne(user);
            SharkUtil.fillStringProperties(userTag, user);

            getContext().setCurrentFBUser(user);
            getContext().setCurrentUserPeerSemanticTag(userTag);

            if (getSharkKb().getOwner() == null) {
                getSharkKb().setOwner(userTag);
            }


        } catch (SharkKBException e) {
            String message = "Importing the current user profile into the SharkKB failed.";
            LOG.error(message, e);
            throw new FBImportException(message, e);
        }
    }
    private PeerSemanticTag getPeerSemanticTagOrCreateOne(User user) throws SharkKBException {
        String id = user.getId();
        String si = FacebookUtil.createUserLink(id);
        String name = user.getName();
        String email = user.getEmail();

        PeerSemanticNet peerSemanticNet = getSharkKb().getPeersAsSemanticNet();
        PeerSemanticTag peerSemanticTag = peerSemanticNet.getSemanticTag(si);

        if (peerSemanticTag == null) {
            peerSemanticTag = peerSemanticNet.createSemanticTag(name, si, email);
            peerSemanticTag.setProperty(FACEBOOK_USER_MARK, "true");
        }

        return peerSemanticTag;
    }

    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.EMAIL, FBPermissions.USER_ABOUT_ME);
    }
}
