package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.facebook.FacebookUtil;
import net.sharkfw.apps.fb.util.shark.KBUtils;
import net.sharkfw.knowledgeBase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Imports the friends of the current imported user.</p>
 *
 * <p>NOTE: Facebook restricts the access to friends in such a way
 * that you can only obtain the friends of the user which are used the same facebook application
 * as the user in order to create a access token. The Term "create" means also that the user have
 * authorized a facebook application to access its data.
 * </p>
 */
@Component
public class FriendsImporter extends BaseFBImporter {

    private Logger LOG = LoggerFactory.getLogger(FriendsImporter.class);

    @Override
    public void performImport() throws FBImportException, SharkKBException {

        User currentFBUser = getContext().getCurrentFBUser();
        PeerSNSemanticTag currentUser = getContext().getCurrentUserPeerSemanticTag();
        PagedList<Reference> friends = getFacebookAPI().friendOperations().getFriends();

        PeerTaxonomy peerTaxonomy = sharkKB.getPeersAsTaxonomy();

        TXSemanticTag facebookUsers = peerTaxonomy.getSemanticTag(FacebookUtil.createFriendsSI(currentFBUser.getId()));


        for (Reference friendRef : friends) {
            PeerSNSemanticTag friendsSemanticTag = KBUtils.createPeerSNTagFrom(friendRef, getSharkKb());
            facebookUsers.move(friendsSemanticTag);
            KBUtils.connectAsFriends(currentUser, friendsSemanticTag);
        }

        LOG.info("Imported friends: " + friends.size());
    }

    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.USER_FRIENDS);
    }

    @Override
    public List<String> getDependentImporters() {
        return Arrays.asList(CurrentUserImporter.class.getName());
    }
}
