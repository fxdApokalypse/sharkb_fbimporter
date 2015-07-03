package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.BaseFBImporterTests;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.social.facebook.api.UserOperations;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class CurrentUserProfileImporterTests extends BaseFBImporterTests {

    @Autowired
    private CurrentUserImporter currentUserImporter;

    @Test
    public void ProfileImporter_ImportUser_UserIsInKB() throws FBImportException, SharkKBException {
        mockServer.expect(requestTo(getFBUrl("me", UserOperations.PROFILE_FIELDS)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(testResponse("me"));

        currentUserImporter.performImport();

        String userLink = "https://www.facebook.com/app_scoped_user_id/11111111/";
        PeerSemanticTag userTag = getKB().getPeerSemanticTag(userLink);
;
        Assert.assertNotNull("No PeerSemanticTag was imported", userTag);
        Assert.assertEquals("Hal Emmerich", userTag.getName());
        Assert.assertArrayEquals(new String[]{"sagIchDirNicht@MegaMail.com"}, userTag.getAddresses());
    }

}
