package net.sharkfw.apps.fb.core.importplan;

import net.sharkfw.apps.fb.core.importer.FBImporterStep;
import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.apps.fb.model.FBPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Permission> userPermissions = serviceProvider.getApi().userOperations().getUserPermissions();
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
