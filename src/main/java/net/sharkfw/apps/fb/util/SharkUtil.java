package net.sharkfw.apps.fb.util;

import net.sharkfw.knowledgeBase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.FacebookObject;
import org.springframework.social.facebook.api.Reference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * Helper Methods for handling {@link SharkKB} tasks.
 */
public class SharkUtil  {

    private static final Logger LOG = LoggerFactory.getLogger(SharkUtil.class);

    /**
     * <p>Retrieves a PeerSemanticTag for a specific FBUser by a User Reference
     * or create one for the corresponding user. The reference
     * id is converted to SI and is used for querying the {@link SharkKB} for the
     * PeerSemanticTag</p>
     *
     * @param ref the reference for corresponding user.
     * @param kb the underlying shark kb.
     * @return the corresponding {@link PeerSNSemanticTag} for the requested user.
     * @throws SharkKBException if there was an error accessing the SharkKB.
     */
    public static PeerSNSemanticTag getPeerSemanticTagBy(Reference ref, SharkKB kb) throws SharkKBException {

        String si = FacebookUtil.createUserLink(ref.getId());
        PeerSemanticNet psn = kb.getPeersAsSemanticNet();
        PeerSNSemanticTag userPT = psn.getSemanticTag(si);
        if (userPT == null) {
            userPT = psn.createSemanticTag(ref.getName(), si, (String) null);
        }
        return userPT;
    }

    /**
     * Converts a PeerSemanticTag to a PeerSNSemanticTag.
     *
     * @param pst the to be converted PeerSemanticTag
     * @param kb the underlying sharkKB
     * @return the converted {@link PeerSNSemanticTag}
     * @throws SharkKBException if there was an error converting the PeerSemanticTag.
     */
    public static PeerSNSemanticTag getPeerSemantigTagAsPeerSNSemanticTag(PeerSemanticTag pst, SharkKB kb ) throws SharkKBException {
        PeerSemanticNet psn = kb.getPeersAsSemanticNet();
        return psn.getSemanticTag(pst.getSI());
    }

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
