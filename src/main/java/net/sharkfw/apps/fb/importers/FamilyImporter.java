package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.KBUtils;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import net.sharkfw.knowledgeBase.PeerSemanticNet;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.springframework.social.facebook.api.FamilyMember;
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
public class FamilyImporter extends BaseFBImporter {

    @Override
    public void performImport() throws FBImportException, SharkKBException {

        PeerSNSemanticTag currentUser = getContext().getCurrentUserPeerSemanticTag();
        PagedList<FamilyMember> familyMembers = getFacebookAPI().friendOperations().getFamily();

        for (FamilyMember familyMember : familyMembers) {
            String relationShip = familyMember.getRelationship();
            PeerSNSemanticTag familyMemberSemanticTag = KBUtils.createPeerSNTagFrom(familyMember, getSharkKb());
            KBUtils.connectBidirectional(relationShip, currentUser, familyMemberSemanticTag);
        }
    }

    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.USER_RELATIONSHIPS);
    }

    @Override
    public List<String> getDependentImporters() {
        return Arrays.asList(CurrentUserImporter.class.getName());
    }
}
