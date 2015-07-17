package net.sharkfw.apps.fb.util.shark;

import net.sharkfw.knowledgeBase.*;
import org.springframework.util.ReflectionUtils;
import static net.sharkfw.apps.fb.util.ReflectionFilters.*;

public class ContextCoordinatesBuilder {

    public static final ReflectionUtils.FieldFilter IS_CC_COORDINATE_FIELD =
        And(Not(fieldWithName("sharkKB")), IS_NOT_PUBLIC_STATIC_FINAL);

    public static ContextCoordinatesBuilder create(SharkKB sharkKB) {
        return new ContextCoordinatesBuilder(sharkKB);
    }

    public enum Direction {
        DIRECTION_IN,
        DIRECTION_OUT,
        DIRECTION_INOUT,
        DIRECTION_NOTHING
    }

    private SemanticTag topic;
    private PeerSemanticTag peer;
    private PeerSemanticTag remotePeer;
    private PeerSemanticTag orginator;
    private TimeSemanticTag time;
    private SpatialSemanticTag location;
    private Direction direction;
    protected SharkKB sharkKB;

    public ContextCoordinatesBuilder(SharkKB sharkKB) {
        reset();
        this.sharkKB = sharkKB;
    }

    public ContextCoordinatesBuilder topic(SemanticTag topic) {
        this.topic = topic;
        return this;
    }

    public ContextCoordinatesBuilder peer(PeerSemanticTag peer) {
        this.peer = peer;
        return this;
    }

    public ContextCoordinatesBuilder remotePeer(PeerSemanticTag remotePeer) {
        this.remotePeer = remotePeer;
        return this;
    }

    public ContextCoordinatesBuilder orginator(PeerSemanticTag orginator) {
        this.orginator = orginator;
        return this;
    }

    public ContextCoordinatesBuilder time(TimeSemanticTag time) {
        this.time = time;
        return this;
    }

    public ContextCoordinatesBuilder location(SpatialSemanticTag location) {
        this.location = location;
        return this;
    }

    public ContextCoordinatesBuilder direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public ContextCoordinatesBuilder reset() {
        ReflectionUtils.doWithFields(getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            field.set(this, null);
        }, IS_CC_COORDINATE_FIELD);
        this.direction = Direction.DIRECTION_NOTHING;
        return this;
    }

    public ContextCoordinates build() throws SharkKBException {
        ContextCoordinates cc = sharkKB.createContextCoordinates(
            topic, orginator, peer,
            remotePeer, time, location,
            direction.ordinal()
        );
        return cc;
    }

    public ContextPoint createContextPoint() throws SharkKBException {
        ContextCoordinates cc = build();
        ContextPoint cp = sharkKB.getContextPoint(cc);
        if (cp == null) {
            cp = sharkKB.createContextPoint(cc);
        }

        return cp;
    };
}
