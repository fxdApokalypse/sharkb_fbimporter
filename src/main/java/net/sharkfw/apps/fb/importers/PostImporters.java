package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.apps.fb.util.facebook.FacebookContextCoordinateBuilder;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PostImporters extends BaseFBImporter {

    private Logger LOG = LoggerFactory.getLogger(PostImporters.class);

    public void performImport() throws FBImportException, SharkKBException {
        PeerSemanticTag userTag = getContext().getCurrentUserPeerSemanticTag();
        PagedList<Post> feed = getFacebookAPI().feedOperations().getFeed();


        for ( Post post : feed ) {
            try {
                importPost(post, userTag);
            } catch(SharkKBException ex) {
                LOG.error("Import of the post with id= " + post.getId() + " failed with the error: " + ex.getMessage(), ex);
            }
        }
    }

    private void importPost(Post post, PeerSemanticTag orginator) throws SharkKBException {

        Post.PostType type = post.getType();
        LOG.info("Import post of the type: " + type.toString());
        switch( type ) {
            case LINK:
                importLinkPost(post, orginator);
            break;
            case PHOTO:
                importPhotoPost(post, orginator);
            break;
            case STATUS:
                importStatusPost(post, orginator);
            break;
            case VIDEO:
                importStatusVideo(post, orginator);
            break;
            case UNKNOWN:
                importUnknowPostType(post, orginator);
            break;
            default:
                LOG.error("The post type: '" + type.toString() + "' isn't supported by this importer.");
        }
    }

    private void importLinkPost(Post post, PeerSemanticTag orginator) throws SharkKBException {

        FacebookContextCoordinateBuilder ccBuilder = new FacebookContextCoordinateBuilder(this);

        ccBuilder
            .time(post.getCreatedTime())
            .location(post.getPlace())
            .direction(post.getPrivacy());

        System.out.println("ID = " + post.getId());
        System.out.println("Name = " + post.getName());
        System.out.println("Link = " + post.getLink());
        System.out.println("Type = " + post.getType().toString());
        System.out.println("StatusType = " + post.getStatusType());
        System.out.println("Caption =" + post.getCaption());
        System.out.println("Description = " + post.getDescription());
        System.out.println("Message =" +  post.getMessage());
        System.out.println("Story = " + post.getStory() );
        System.out.println("CreatedDate = " +post.getCreatedTime().toString());
        System.out.println("UpdateTime = " + post.getUpdatedTime());
        System.out.println("Applications = " + (post.getApplication() != null  ? post.getApplication().getName() : ""));
        System.out.println("Place = " + (post.getPlace() != null ? post.getPlace().getName() : "" ));
        System.out.println("Source = " + post.getSource());
        // System.out.println("To = " + post.getTo() != null ? post.getTo().stream().map((ref) -> ref.getName()).collect(Collectors.joining(",")) : "");
        System.out.println("From = " + post.getFrom().getName());
        // System.out.println("Actions = " + post.getActions() != null ? post.getActions().stream().map(Object::toString).collect(Collectors.joining(", ")) : "");
        System.out.println("Icon = " + post.getIcon());
        System.out.println("Place = " + post.getPlace());
        System.out.println("Pictures = " + post.getPicture());
        System.out.println("ObjectID = " + post.getObjectId());
        System.out.println("Tags = " + post.getMessageTags());
        System.out.println("Properties = " + post.getProperties());
        System.out.println("Shares = " + post.getShares());
        System.out.println("AdminCreator = " + post.getAdminCreator());
        System.out.println("Extra Data = " + post.getExtraData());
        // System.out.println("With Tags = " + post.getWithTags() != null ? post.getWithTags().stream().map((ref) -> ref != null ? ref.getName() :"").collect(Collectors.joining(", ")) : "");
        System.out.println(" = " + post.toString() );
        System.out.println("=======================================");
    }

    private void importPhotoPost(Post post, PeerSemanticTag orginator) {

    }

    private void importStatusPost(Post post, PeerSemanticTag orginator) {

    }

    private void importStatusVideo(Post post, PeerSemanticTag orginator) {

    }

    private void importUnknowPostType(Post post, PeerSemanticTag orginator) {

    }

    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.USER_POSTS);
    }

    @Override
    public List<String> getDependentImporters() {
        return Arrays.asList(CurrentUserImporter.class.getName(), FriendsImporter.class.getName());
    }
}
