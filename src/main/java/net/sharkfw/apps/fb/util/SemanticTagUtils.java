package net.sharkfw.apps.fb.util;

import net.sharkfw.knowledgeBase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.FacebookObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
                LOG.info(
                    String.format("Set property for semantic tag %s - %s = %s", toString(semanticTag.getSI()), property, value)
                );
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
}
