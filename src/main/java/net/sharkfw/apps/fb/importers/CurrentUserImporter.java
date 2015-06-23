package net.sharkfw.apps.fb.importers;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.model.FBPermissions;
import net.sharkfw.knowledgeBase.PeerSemanticNet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class CurrentUserImporter extends BaseFBImporter {

    public static final String FACEBOOK_USER_MARK = "is_facebook_user";
    private static final String GETTER_PREFFIX = "get";

    private Logger LOG = LoggerFactory.getLogger(CurrentUserImporter.class);

    public void performImport() throws FBImportException {
        PeerSemanticTag userTag = null;
        UserOperations userOperations = getFacebookAPI().userOperations();
        User user = userOperations.getUserProfile();

        try {
            userTag = getPeerSemanticTagOrCreateOne(user);
            fillProperties(userTag, user);
        } catch (SharkKBException e) {
            String message = "Importing the current user profile into the SharkKB failed.";
            LOG.error(message, e);
            throw new FBImportException(message, e);
        }
    }
    private PeerSemanticTag getPeerSemanticTagOrCreateOne(User user) throws SharkKBException {
        String si = user.getLink();
        String name = user.getName();
        String email = user.getEmail();

        PeerSemanticNet peerSemanticNet = getSharkKb().getPeersAsSemanticNet();
        PeerSemanticTag peerSemanticTag = peerSemanticNet.getSemanticTag(si);

        if (peerSemanticTag == null) {
            peerSemanticTag = peerSemanticNet.createSemanticTag(name, si, email);
            peerSemanticTag.setProperty(FACEBOOK_USER_MARK, "true");
        }


        return peerSemanticTag;
    }

    private void fillProperties(PeerSemanticTag userTag, User user) throws SharkKBException {
        Class<?>  userClass =  user.getClass();
        Method[] methods = userClass.getMethods();

        for ( Method method : methods ) {
            if (!method.getName().startsWith(GETTER_PREFFIX)) continue;
            if ( method.getName().length() <= 3) continue;
            if ( method.getParameterCount() > 0 ) continue;
            if ( method.getReturnType() != String.class) continue;

            String property =  "fb_user_" + method.getName().substring(GETTER_PREFFIX.length()).toLowerCase();
            try {
                userTag.setProperty(property, (String) method.invoke(user));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                LOG.error(String.format("Access the getter '%s' failed", method.getName()), e);
            }

        }
    }

    @Override
    public List<String> getRequiredPermissions() {
        return Arrays.asList(FBPermissions.EMAIL, FBPermissions.USER_ABOUT_ME);
    }
}
