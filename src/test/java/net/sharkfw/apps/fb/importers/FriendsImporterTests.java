package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.BaseFBImporterTests;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.util.FacebookUtil;
import net.sharkfw.apps.fb.util.KBUtils;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import net.sharkfw.knowledgeBase.SNSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;


public class FriendsImporterTests extends BaseFBImporterTests {

    @Autowired
    private FriendsImporter friendsImporter;
    private PeerSNSemanticTag userTag;

    @Before
    public void setUPTestEnvironment() throws SharkKBException {
        mockServer.expect(requestTo(getFBUrl("me/friends")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(testResponse("friends"));

        userTag = KBUtils.createPeerSNTagFrom(getTestJSONObject("me", User.class), getKB());
        friendsImporter.getContext().setCurrentUserPeerSemanticTag(userTag);
    }

    @Test
    public void FriendsImporterTests_importFriends_AllFriendEdgesAreCreated() throws FBImportException, SharkKBException, IOException {
        friendsImporter.performImport();

        int expectedCountOfFriends = getExpectedFriends().size();

        List<SNSemanticTag> friendSrcEdges = getFriendshipSrcEdgesFromTestUser();
        List<SNSemanticTag> friendsTgtEdges = getFriendshipTgtEdgesFromTestUser();

        Assert.assertEquals("Not all Friendship edges are imported", expectedCountOfFriends, friendSrcEdges.size());
        Assert.assertEquals("Friendship must be a unidirectional edge", expectedCountOfFriends, friendsTgtEdges.size());
    }

    @Test
    public void FriendsImporterTests_importFriends_AllFriendNodesAreCreatedAsExpected() throws FBImportException, SharkKBException, IOException {

        friendsImporter.performImport();

        List<Reference> expectedFriendsOfHal = getExpectedFriends();

        List<SNSemanticTag> friendSrcEdges = getFriendshipSrcEdgesFromTestUser();
        List<SNSemanticTag> friendsTgtEdges = getFriendshipTgtEdgesFromTestUser();

        assertEquals(expectedFriendsOfHal, friendSrcEdges);
        assertEquals(expectedFriendsOfHal, friendsTgtEdges);
    }

    private void assertEquals(List<Reference> expectedFriendsOfHal, List<SNSemanticTag> friendSrcEdges) {
        for (int i = 0; i < expectedFriendsOfHal.size(); i++) {
            Reference expectedFriend = expectedFriendsOfHal.get(i);
            SNSemanticTag actualFriendTag = friendSrcEdges.get(i);

            Assert.assertEquals(FacebookUtil.createUserLink(expectedFriend.getId()), actualFriendTag.getSI()[0]);
            Assert.assertEquals(expectedFriend.getName(), actualFriendTag.getName());
        }
    }

    private List<Reference> getExpectedFriends() {
        return getTestJSONList("friends", Reference.class);
    }

    private List<SNSemanticTag> getFriendshipSrcEdgesFromTestUser() {
        return Collections.list(userTag.sourceTags(KBUtils.FRIENDSHIP_EDGE));
    }

    private List<SNSemanticTag> getFriendshipTgtEdgesFromTestUser() {
        return Collections.list(userTag.targetTags(KBUtils.FRIENDSHIP_EDGE));
    }
}
