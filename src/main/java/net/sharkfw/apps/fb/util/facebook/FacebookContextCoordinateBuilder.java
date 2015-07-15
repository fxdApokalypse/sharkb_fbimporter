package net.sharkfw.apps.fb.util.facebook;

import net.sharkfw.apps.fb.core.importer.FBImporter;
import net.sharkfw.apps.fb.util.shark.ContextCoordinatesBuilder;
import net.sharkfw.apps.fb.util.shark.SemanticTagUtils;
import net.sharkfw.knowledgeBase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Location;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.Post;

import java.util.Date;

public class FacebookContextCoordinateBuilder extends ContextCoordinatesBuilder {

    public static FacebookContextCoordinateBuilder create(FBImporter importer) {
        return new FacebookContextCoordinateBuilder(importer);
    }
    public static final Logger LOG = LoggerFactory.getLogger(FacebookContextCoordinateBuilder.class);

    private FBImporter importer;

    public FacebookContextCoordinateBuilder(FBImporter importer) {
        super(importer.getSharkKb());
        this.importer = importer;
    }

    public FacebookContextCoordinateBuilder location(Page locationPage) throws SharkKBException {
        location(SemanticTagUtils.createSpatialSemanticTag(locationPage.getLocation(), sharkKB));
        return this;
    }

    public FacebookContextCoordinateBuilder location(Location location) throws SharkKBException {
        location(SemanticTagUtils.createSpatialSemanticTag(location, sharkKB));
        return this;
    }

    public FacebookContextCoordinateBuilder time(Date time) throws SharkKBException {
        time(time, 0);
        return this;
    }

    public FacebookContextCoordinateBuilder time(Date time, long duration) throws SharkKBException {
        time(SemanticTagUtils.createTimeSemanticTag(time, duration, sharkKB));
        return this;
    }

    public FacebookContextCoordinateBuilder direction(Post.Privacy privacy) throws SharkKBException {
        direction(Direction.DIRECTION_OUT);
        switch (privacy.getValue()) {
            case SELF:
                direction(Direction.DIRECTION_NOTHING);
            break;
            case EVERYONE:
                remotePeer(null);
            case ALL_FRIENDS:
            case FRIENDS_OF_FRIENDS:
                remotePeer(getFriendsTag());
            break;
            case CUSTOM:
            break;
            default:
                LOG.error("Found Unsupported Post.Privacy settings: " + privacy.getValue().toString());
        }
        return this;
    }

    public PeerSemanticTag getFriendsTag() throws SharkKBException {
        return null;
    }
}
