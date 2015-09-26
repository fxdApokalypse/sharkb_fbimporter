package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporterStep;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.facebook.FacebookContextCoordinateBuilder;
import net.sharkfw.apps.fb.util.shark.ContextCoordinatesBuilder;
import net.sharkfw.apps.fb.util.shark.KBUtils;
import net.sharkfw.knowledgeBase.*;
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
public class CurrentUserImporterStep extends BaseFBImporterStep {

    private Logger LOG = LoggerFactory.getLogger(CurrentUserImporterStep.class);

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

            ContextCoordinates cc = FacebookContextCoordinateBuilder
                .create(this)
                .topic(userTag.getSuperTag()) // Assign this as facebook user.
                .peer(userTag)
                .orginator(userTag)
                .direction(ContextCoordinatesBuilder.Direction.DIRECTION_OUT)
                .build();


            ContextPoint cp = sharkKB.getContextPoint(cc);

        } catch (SharkKBException e) {
            String message = "Importing the current user profile into the SharkKB failed.";
            LOG.error(message, e);
            throw new FBImportException(message, e);
        }
    }


    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.EMAIL, FBPermissions.PUBLIC_PROFILE);
    }
}
