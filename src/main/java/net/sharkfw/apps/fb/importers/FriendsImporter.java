package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.FacebookUtil;
import net.sharkfw.knowledgeBase.*;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;
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

    /**
     * The name of the {@link SNSemanticTag} predicate which
     * models the friendship relationship between {@link PeerSemanticTag}.
     */
    public static final String FRIENDSHIP_EDGE = "friends";

    @Override
    public void performImport() throws FBImportException, SharkKBException {

        SharkKB kb = getSharkKb();
        PeerSemanticNet psNet = kb.getPeersAsSemanticNet();
        PeerSNSemanticTag currentUser = psNet.getSemanticTag(getContext().getCurrentUserPeerSemanticTag().getSI());

        PagedList<Reference> friends = getFacebookAPI().friendOperations().getFriends();
        for (Reference friendRef : friends) {
            String si = FacebookUtil.createUserLink(friendRef.getId());
            PeerSNSemanticTag friendsSemanticTag = psNet.createSemanticTag(friendRef.getName(), si, (String) null);

            currentUser.setPredicate(FRIENDSHIP_EDGE, friendsSemanticTag);
            friendsSemanticTag.setPredicate(FRIENDSHIP_EDGE, currentUser);
        }

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
