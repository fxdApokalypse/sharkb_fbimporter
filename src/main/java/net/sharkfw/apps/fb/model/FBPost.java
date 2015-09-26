package net.sharkfw.apps.fb.model;

import net.sharkfw.apps.fb.databind.ContextPointMapper;
import net.sharkfw.knowledgeBase.ContextPoint;
import org.springframework.social.facebook.api.*;

import java.util.Date;
import java.util.List;

public class FBPost extends ContextPointMapper {

    public static FBPost create(Post post, ContextPoint contextPoint) {

        FBPost fbPost = new FBPost(contextPoint);
        fbPost.setId(post.getId());
        fbPost.setName(post.getName());
        fbPost.setMessage(post.getMessage());
        fbPost.setActions(post.getActions());
        fbPost.setApplication(post.getApplication());
        fbPost.setCaption(post.getCaption());
        fbPost.setCreatedTime(post.getCreatedTime());
        fbPost.setUpdateTime(post.getUpdatedTime());
        fbPost.setDescription(post.getDescription());
        fbPost.setFrom(post.getFrom());
        fbPost.setHidden(post.isHidden());
        fbPost.setIcon(post.getIcon());
        fbPost.setIsPublished(post.isPublished());
        fbPost.setLink(post.getLink());
        fbPost.setObjectId(post.getObjectId());
        fbPost.setPlace(post.getPlace());
        fbPost.setPicture(post.getPicture());
        fbPost.setPrivacy(post.getPrivacy());
        fbPost.setProperties(post.getProperties());
        fbPost.setShares(post.getShares());
        fbPost.setSource(post.getSource());
        fbPost.setStatusType(post.getStatusType());
        fbPost.setStory(post.getStory());
        fbPost.setTo(post.getTo());
        fbPost.setType(post.getType());
        fbPost.setWithTags(post.getWithTags());
        return fbPost;
    }


    public static final String ID = "id";
    public static final String ACTIONS = "actions";
    public static final String APPLICATION = "application";
    public static final String CAPTION = "caption";
    public static final String CREATED_TIME = "createdTime";
    public static final String DESCRIPTION = "description";
    public static final String FROM = "from";
    public static final String HIDDEN = "hidden";
    public static final String ICON = "icon";
    public static final String PUBLISHED = "published";
    public static final String LINK = "link";
    public static final String MESSAGE = "message";
    public static final String NAME = "name";
    public static final String OBJECT_ID = "objectId";
    public static final String PICTURE = "picture";
    public static final String PAGE = "page";
    public static final String PRIVACY = "privacy";
    public static final String SOURCE = "source";
    public static final String PROPERTIES = "properties";
    public static final String STATUS_TYPE = "statusType";
    public static final String STORY = "story";
    public static final String TO = "to";
    public static final String TYPE = "type";
    public static final String UPDATE_TIME = "updateTime";
    public static final String WITH_TAGS = "withTags";
    public static final String SHARES = "shares";

    public FBPost(ContextPoint userContextPoint) {
        super(userContextPoint);
    }

    public Integer getShares() {
        return readInformation(SHARES, Integer.class);
    }

    public void setShares(Integer shares) {
        writeInformation(SHARES, shares);
    }

    public List<Reference> getWithTags() {
        return readInformationList(WITH_TAGS, Reference.class);
    }

    public void setWithTags(List<Reference> withTags) {
        writeInformationList(WITH_TAGS, withTags, Reference.class);
    }

    public Date getUpdatedTime() {
        return readInformation(UPDATE_TIME, Date.class);
    }

    public void setUpdateTime(Date updateTime) {
        writeInformation(UPDATE_TIME, updateTime);
    }

    public Post.PostType getType() {
        return readInformation(TYPE, Post.PostType.class);
    }

    public void setType(Post.PostType postType) {
        writeInformation(TYPE, postType);
    }

    public List<Reference> getTo() {
        return readInformationList(TO, Reference.class);
    }

    public void setTo(List<Reference> to) {
        writeInformationList(TO, to, Reference.class);
    }

    public String getStory() {
        return readInformation(STORY, String.class);
    }

    public void setStory(String story) {
        writeInformation(STORY, story);
    }

    public Post.StatusType getStatusType() {
        return readInformation(STATUS_TYPE, Post.StatusType.class);
    }

    public void setStatusType(Post.StatusType statusType) {
        writeInformation(STATUS_TYPE, statusType);
    }

    public List<PostProperty> getProperties() {
        return readInformationList(PROPERTIES, PostProperty.class);
    }

    public void setProperties(List<PostProperty> properties) {
        writeInformationList(PROPERTIES, properties, PostProperty.class);
    }

    public String getSource() {
        return readInformation(SOURCE, String.class);
    }

    public void setSource(String source) {
        writeInformation(SOURCE, source);
    }

    public Post.Privacy getPrivacy() {
        return readInformation(PRIVACY, Post.Privacy.class);
    }

    public void setPrivacy(Post.Privacy privacy) {
        writeInformation(PRIVACY, privacy);
    }

    public Page getPlace() {
        return readInformation(PAGE, Page.class);
    }

    public void setPlace(Page page) {
        writeInformation(PAGE, page);
    }

    public String getPicture() {
        return readInformation(PICTURE, String.class);
    }

    public void setPicture(String picture) {
        writeInformation(PICTURE, picture);
    }

    public String getObjectId() {
        return readInformation(OBJECT_ID, String.class);
    }

    public void setObjectId(String objectId) {
         writeInformation(OBJECT_ID, String.class);
    }

    public String getName() {
        return readInformation(NAME, String.class);
    }

    public void setName(String name) {
        writeInformation(NAME, name);
    }

    public String getMessage() {
        return readInformation(MESSAGE, String.class);
    }

    public void setMessage(String message) {
        writeInformation(MESSAGE, message);
    }

    public String getLink() {
        return readInformation(LINK, String.class);
    }

    public void setLink(String link) {
        writeInformation(LINK, link);
    }

    public boolean isPublished() {
        return readInformation(PUBLISHED, Boolean.class);
    }

    public void setIsPublished(Boolean isPublished) {
        writeInformation(PUBLISHED, isPublished);
    }

    public boolean isHidden() {
        return readInformation(HIDDEN, Boolean.class);
    }

    public void setHidden(Boolean isHidden) {
        writeInformation(HIDDEN, isHidden);
    }

    public String getIcon() {
        return readInformation(ICON, String.class);
    }

    public void setIcon(String icon) {
        writeInformation(ICON, icon);
    }

    public Reference getFrom() {
        return readInformation(FROM, Reference.class);
    }

    public void setFrom(Reference from) {
        writeInformation(FROM, from);
    }

    public String getDescription() {
        return readInformation(DESCRIPTION, String.class);
    }

    public void setDescription(String description) {
        writeInformation(DESCRIPTION, description);
    }

    public Date getCreatedTime() {
        return readInformation(CREATED_TIME, Date.class);
    }

    public void setCreatedTime(Date createdTime) {
        writeInformation(CREATED_TIME, createdTime);
    }

    public String getCaption() {
        return readInformation(CAPTION, String.class);
    }

    public void setCaption(String caption) {
        writeInformation(CAPTION, caption);
    }

    public Reference getApplication() {
        return readInformation(APPLICATION, Reference.class);
    }

    public void setApplication(Reference reference) {
        writeInformation(APPLICATION, reference);
    }

    public List<Action> getActions() {
        return readInformationList(ACTIONS, Action.class);
    }

    public void setActions(List<Action> actions) {
        writeInformationList(ACTIONS, actions, Action.class);
    }

    public String getId() {
        return readInformation(ID, String.class);
    }

    public void setId(String id) {
        writeInformation(ID, id);
    }

}
