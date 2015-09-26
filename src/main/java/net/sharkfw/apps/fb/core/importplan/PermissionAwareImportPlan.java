package net.sharkfw.apps.fb.core.importplan;

import net.sharkfw.apps.fb.FacebookLogin;
import net.sharkfw.apps.fb.FacebookLoginException;
import net.sharkfw.apps.fb.core.importer.FBImporterStep;
import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.apps.fb.model.FBPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.facebook.api.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionAwareImportPlan extends BaseImportPlan {


    @Autowired
    protected FacebookServiceProvider serviceProvider;

    private FBPermissions grantedPermissions;


    @Override
    protected void retrieveImportSteps() {
        LOG.info("Check permissions for the import steps");

        List<Permission> userPermissions = null;
        try {
            userPermissions = serviceProvider.getApi().userOperations().getUserPermissions();
        } catch(ResourceNotFoundException ex) {
            LOG.warn("Access token expired or not found");
            String [] requiredPermissionsArray = requiredPermissions.toArray(new String[0]);
            FacebookLogin.FacebookLoginResponse response =  FacebookLogin.invokeLogin(requiredPermissionsArray);
            if (response.isFailed()) {
                String loginError = "The login failed: " + response;
                LOG.error(loginError);
                throw new FacebookLoginException(loginError);
            }
            serviceProvider.update(response.getAccessToken());
            userPermissions = serviceProvider.getApi().userOperations().getUserPermissions();
        }
        grantedPermissions = FBPermissions.from(userPermissions);
        super.retrieveImportSteps();
    }

    @Override
    protected boolean includeImporter(FBImporterStep fbImporterStep) {
        boolean granted = grantedPermissions.hasGranted(fbImporterStep.getRequiredPermissions());
        if (!granted) {
            List<String> nonGrantedPermissions = grantedPermissions.nonGrantedPermissions(fbImporterStep.getRequiredPermissions());
            LOG.error(String.format(
                    "Missing permissions for the import step \"%s\": %s",
                    fbImporterStep.getName(),
                    String.join(",", nonGrantedPermissions)));
        }

        return granted;
    }
}
