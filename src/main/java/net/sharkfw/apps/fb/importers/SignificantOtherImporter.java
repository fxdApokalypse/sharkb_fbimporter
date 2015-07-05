package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.KBUtils;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Imports the significant or better known as the partner of
 * the current importing user. This importer creates the corresponding
 * user peer semantic tag if it isn't already in the underlying SharkKB
 */
@Component
public class SignificantOtherImporter extends BaseFBImporter {

    private static final Logger LOG = LoggerFactory.getLogger(SignificantOtherImporter.class);

    /**
     * The predicate name which is used to describe the relationship
     * between two PeerSemanticTags.
     */
    public static final String IN_A_RELATIONSHIP = "in_a_relationship";

    @Override
    public void performImport() throws FBImportException, SharkKBException {
        PeerSNSemanticTag currentUserTag = getContext().getCurrentUserPeerSemanticTag();

        Reference significantOther = getFacebookAPI().userOperations().getUserProfile().getSignificantOther();

        if (significantOther == null) {
            LOG.warn(String.format("The significant other of the user '%s' isn't retrieve able", currentUserTag.getSI()));
            return;
        }

        PeerSNSemanticTag significantOtherTag = KBUtils.createPeerSNTagFrom(significantOther, getSharkKb()) ;
        KBUtils.connectBidirectional(IN_A_RELATIONSHIP, currentUserTag, significantOtherTag);
    }

    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.USER_RELATIONSHIP_DETAILS, FBPermissions.USER_RELATIONSHIPS);
    }

    @Override
    public List<String> getDependentImporters() {
        return Arrays.asList(CurrentUserImporter.class.getName());
    }
}
