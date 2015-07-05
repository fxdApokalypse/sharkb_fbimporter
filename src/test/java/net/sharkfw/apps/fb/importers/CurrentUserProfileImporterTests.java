package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.BaseFBImporterTests;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.util.FacebookUtil;
import net.sharkfw.apps.fb.util.KBUtil;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.TXSemanticTag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class CurrentUserProfileImporterTests extends BaseFBImporterTests {

    @Autowired
    private CurrentUserImporter currentUserImporter;

    @Before
    public void setUpMockServer() {
        mockServer.expect(requestTo(getFBUrl("me", UserOperations.PROFILE_FIELDS)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(testResponse("me"));
    }

    @Test
    public void ProfileImporter_ImportUser_UserIsInKB() throws FBImportException, SharkKBException {
        currentUserImporter.performImport();

        User user = getTestJSONObject("me", User.class);
        PeerSNSemanticTag userTag = getKB().getPeersAsSemanticNet().getSemanticTag(FacebookUtil.createUserLink(user.getId()));


        Assert.assertNotNull("No PeerSemanticTag for the User was imported", userTag);
        Assert.assertEquals(user.getName(), userTag.getName());
        Assert.assertArrayEquals(new String[]{user.getEmail()}, userTag.getAddresses());
    }

    @Test
    public void ProfileImporter_ImportUser_ImportedUserIsUnderTheTaxonomyFacebookUser() throws FBImportException {
        currentUserImporter.performImport();

        PeerSNSemanticTag importedUserTag = currentUserImporter.getContext().getCurrentUserPeerSemanticTag();
        TXSemanticTag facebookUsers = importedUserTag.getSuperTag();

        Assert.assertNotNull("A imported user have to be the sub tag of a Facebook Users Taxonomy", facebookUsers);
        Assert.assertArrayEquals(new String[]{KBUtil.FACEBOOK_USERS_TAXONOMY_SI}, facebookUsers.getSI());

    }

    @Test
    public void ProfileImporter_ImportUser_ImportedUserMustBeTheOwnerOfTheKB() throws FBImportException {
        currentUserImporter.performImport();
        PeerSNSemanticTag importedUserTag = currentUserImporter.getContext().getCurrentUserPeerSemanticTag();
        Assert.assertEquals(importedUserTag, getKB().getOwner());
    }

}
