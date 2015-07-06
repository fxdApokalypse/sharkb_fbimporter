package net.sharkfw.apps.fb.util;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.geom.SharkGeometry;
import net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.FacebookObject;
import org.springframework.social.facebook.api.Location;
import org.springframework.social.facebook.api.Page;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * Helper Methods for handling {@link SharkKB} tasks.
 */
public class SemanticTagUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SemanticTagUtils.class);
    private static final String GETTER_PREFIX = "get";

    /**
     * Fill properties of a semantic tag
     * by all string getters of Facebook object.
     *
     * @param semanticTag the semantic tag which is receiving the string properties.
     * @param fbObject the facebook object which contains all the string properties
     * @throws SharkKBException if the writing of the properties is failed
     */
    public static void fillStringProperties(SemanticTag semanticTag, FacebookObject fbObject) throws SharkKBException {
        Class<?>  userClass =  fbObject.getClass();
        Method[] methods = userClass.getMethods();
        for ( Method method : methods ) {
            if (!method.getName().startsWith(GETTER_PREFIX)) continue;
            if ( method.getName().length() <= 3) continue;
            if ( method.getParameterCount() > 0 ) continue;
            if ( method.getReturnType() != String.class) continue;

            String property =  "fb." + userClass.getSimpleName() + "." + method.getName().substring(GETTER_PREFIX.length()).toLowerCase();
            try {
                String value = (String) method.invoke(fbObject);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(
                        String.format("Set property for semantic tag %s - %s = %s", toString(semanticTag.getSI()), property, value)
                    );
                }
                semanticTag.setProperty(property, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                LOG.error(String.format("Access the getter '%s' failed", method.getName()), e);
            }

        }
    }

    public static String toString(String si[]) {
        return "[" + Arrays.stream(si).collect(Collectors.joining(", ")) + "]";
    }

    public static TimeSemanticTag createTimeSemanticTag(Date from, long duration, SharkKB kb ) throws SharkKBException {
        return kb.getTimeSTSet().createTimeSemanticTag(from.getTime(), duration);
    }

    public static SpatialSemanticTag createSpatialSemanticTag(Page placePage, SharkKB kb) throws SharkKBException {

        if (placePage.getLocation() == null) {
            throw new IllegalArgumentException("The page don't provide a required location attribute. Maybe this page isn't a location page.");
        }
        Location location = placePage.getLocation();

        // TODO: implement this geometry properly when i receive the wisdom of the magic WKT, EWKT and SRS.
        SharkGeometry geometry = InMemoSharkGeometry.createGeomByEWKT(String.format("POINT(%f, %f)", location.getLatitude(), location.getLongitude()));
        SpatialSemanticTag ssTag = kb.getSpatialSTSet().createSpatialSemanticTag(placePage.getName(), new String[]{FacebookUtil.createUserLink(placePage.getId())}, geometry);
        fillStringProperties(ssTag, location);
        return ssTag;
    }
}
