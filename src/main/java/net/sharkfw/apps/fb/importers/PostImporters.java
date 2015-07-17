package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporterStep;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.model.FBPost;
import net.sharkfw.apps.fb.model.FBUser;
import net.sharkfw.apps.fb.util.facebook.FacebookContextCoordinateBuilder;
import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class PostImporters extends BaseFBImporterStep {

    private Logger LOG = LoggerFactory.getLogger(PostImporters.class);

    public void performImport() throws FBImportException, SharkKBException {
        PeerSemanticTag userTag = getContext().getCurrentUserPeerSemanticTag();
        PagedList<Post> feed = getFacebookAPI().feedOperations().getFeed();

        for ( Post post : feed ) {
            if (isStoryPost(post)) continue;
            if (isApplicationPost(post)) continue;
            try {
                importPost(post, userTag);
            } catch(SharkKBException ex) {
                LOG.error("Import of the post with id= " + post.getId() + " failed with the error: " + ex.getMessage(), ex);
            }
        }
    }

    private void importPost(Post post, PeerSemanticTag orginator) throws SharkKBException {

        FacebookContextCoordinateBuilder ccBuilder = new FacebookContextCoordinateBuilder(this);
        ContextPoint cp = ccBuilder
            .time(post.getUpdatedTime())
            .location(post.getPlace()).createContextPoint();
        FBPost fbPost = FBPost.create(post, cp);
    }



    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.USER_POSTS);
    }

    @Override
    public List<String> getDependentImporters() {
        return Arrays.asList(CurrentUserImporterStep.class.getName(), FriendsImporterStep.class.getName());
    }

    /**
     * Checks if a post is a story post
     *
     * @param post
     * @return true if this post is a story post.
     */
    public static boolean isStoryPost(Post post) {
        return Objects.nonNull(post.getStory()) && post.getStory() != "";
    }

    /**
     * Checks if a post was created by application.
     *
     * @param post
     * @return true if this post was a application post.
     */
    private boolean isApplicationPost(Post post) {
        return post.getApplication() != null;
    }

    private void logPost(Post post) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("ID = " + post.getId());
            LOG.debug("Name = " + post.getName());
            LOG.debug("Link = " + post.getLink());
            LOG.debug("Type = " + post.getType().toString());
            LOG.debug("StatusType = " + post.getStatusType());
            LOG.debug("Caption =" + post.getCaption());
            LOG.debug("Description = " + post.getDescription());
            LOG.debug("Message =" +  post.getMessage());
            LOG.debug("Story = " + post.getStory() );
            LOG.debug("CreatedDate = " +post.getCreatedTime().toString());
            LOG.debug("UpdateTime = " + post.getUpdatedTime());
            LOG.debug("Applications = " + (post.getApplication() != null  ? post.getApplication().getName() : ""));
            LOG.debug("Place = " + (post.getPlace() != null ? post.getPlace().getName() : "" ));
            LOG.debug("Source = " + post.getSource());
            //LOG.debug("To = " + post.getTo() != null ? post.getTo().stream().map((ref) -> ref.getName()).collect(Collectors.joining(",")) : "");
            LOG.debug("From = " + post.getFrom().getName());
            //LOG.debug("Actions = " + post.getActions() != null ? post.getActions().stream().map(Object::toString).collect(Collectors.joining(", ")) : "");
            LOG.debug("Icon = " + post.getIcon());
            LOG.debug("Place = " + post.getPlace());
            LOG.debug("Pictures = " + post.getPicture());
            LOG.debug("ObjectID = " + post.getObjectId());
            LOG.debug("Tags = " + post.getMessageTags());
            LOG.debug("Properties = " + post.getProperties());
            LOG.debug("Shares = " + post.getShares());
            LOG.debug("AdminCreator = " + post.getAdminCreator());
            LOG.debug("Extra Data = " + post.getExtraData());
            //LOG.debug("With Tags = " + post.getWithTags() != null ? post.getWithTags().stream().map((ref) -> ref != null ? ref.getName() :"").collect(Collectors.joining(", ")) : "");
            LOG.debug(" = " + post.toString() );
            LOG.debug("=======================================");
        }
    }
}
