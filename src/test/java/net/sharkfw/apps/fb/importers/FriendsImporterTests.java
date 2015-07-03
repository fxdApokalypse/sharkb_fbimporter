package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.BaseFBImporterTests;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.util.Enumerations;
import net.sharkfw.apps.fb.util.FacebookUtil;
import net.sharkfw.knowledgeBase.FragmentationParameter;
import net.sharkfw.knowledgeBase.PeerSemanticNet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;


public class FriendsImporterTests extends BaseFBImporterTests {

    @Autowired
    private FriendsImporter friendsImporter;

    @Test
    public void FriendsImporterTests_ImportUser_UserIsInKB() throws FBImportException, SharkKBException {
        mockServer.expect(requestTo(getFBUrl("me/friends", "id", "name")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(testResponse("friends"));

        String id = "11111111";
        String si = FacebookUtil.createUserLink(id);
        String name = "Hal Emmerich";
        String email = "sagIchDirNicht";

        PeerSemanticNet psn = getKB().getPeersAsSemanticNet();
        PeerSemanticTag currentUser = psn.createSemanticTag(name, si, email);
        friendsImporter.getContext().setCurrentUserPeerSemanticTag(currentUser);
        friendsImporter.performImport();

        Vector<String> friendPredicate = new Vector<String>();
        friendPredicate.add(FriendsImporter.FRIENDS_EDGE);
        FragmentationParameter fp = new FragmentationParameter(friendPredicate, null, 3);
        PeerSemanticNet friendNet = psn.fragment(currentUser, fp);

        List<PeerSemanticTag> friends = new ArrayList<>();
        Enumerations.asIterable(friendNet.peerTags()).forEach(friends::add);

        for (int i = 0; i <= friends.size(); i++) {
            Assert.assertEquals("Friend_" + i + 2, friends.get(i).getName());
        }
    }
}
