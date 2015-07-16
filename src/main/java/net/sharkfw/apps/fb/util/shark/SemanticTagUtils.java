package net.sharkfw.apps.fb.util.shark;

import net.sharkfw.apps.fb.util.ReflectionFilters;
import net.sharkfw.apps.fb.util.facebook.FacebookUtil;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.geom.SharkGeometry;
import net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry;
import net.sharkfw.system.SharkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.FacebookObject;
import org.springframework.social.facebook.api.Location;
import org.springframework.social.facebook.api.Page;
import org.springframework.util.ReflectionUtils;

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

        ReflectionUtils.MethodCallback setPropertyByField = (Method method) -> {
            String property = "fb." + userClass.getSimpleName() + "." + method.getName().substring(GETTER_PREFIX.length()).toLowerCase();
            try {
                String value = (String) method.invoke(fbObject);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(
                        String.format("Set property for semantic tag %s - %s = %s", toString(semanticTag.getSI()), property, value)
                    );
                }
                semanticTag.setProperty(property, value);
            } catch (IllegalAccessException | InvocationTargetException | SharkKBException e) {
                e.printStackTrace();
                LOG.error(String.format("Access the getter '%s' failed", method.getName()), e);
            }
        };

        ReflectionUtils.doWithMethods(
            userClass,
            setPropertyByField,
            ReflectionFilters.isGetterMethod(String.class)
        );

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
        return createSpatialSemanticTag(placePage.getLocation(), kb);
    }

    public static SpatialSemanticTag createSpatialSemanticTag(Location location, SharkKB kb) throws SharkKBException {

        SharkGeometry geometry = InMemoSharkGeometry.createGeomByEWKT(String.format("POINT(%f, %f)", location.getLatitude(), location.getLongitude()));
        SpatialSemanticTag ssTag = kb.getSpatialSTSet().createSpatialSemanticTag(location.getName(), new String[]{FacebookUtil.createUserLink(location.getId())}, geometry);
        fillStringProperties(ssTag, location);
        return ssTag;
    }
}
