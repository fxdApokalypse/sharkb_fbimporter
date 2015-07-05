package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.KBUtils;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
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



    private Logger LOG = LoggerFactory.getLogger(CurrentUserImporter.class);

    public void performImport() throws FBImportException {
        PeerSNSemanticTag userTag = null;
        UserOperations userOperations = getFacebookAPI().userOperations();
        User user = userOperations.getUserProfile();

        try {
            userTag = KBUtils.createPeerSNTagFrom(user, getSharkKb());

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


    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.EMAIL, FBPermissions.USER_ABOUT_ME);
    }
}
