package net.sharkfw.apps.fb.core.importer;

import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.knowledgeBase.SharkKB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public abstract class BaseFBImporterStep implements FBImporterStep {

    @Autowired
    protected FacebookServiceProvider serviceProvider;

    @Autowired
    protected  ImporterContext importerContext;

    @Autowired
    @Qualifier("sharkKB")
    protected SharkKB sharkKB;

    @Override
    public FacebookTemplate getFacebookAPI() {
        return serviceProvider.getApi();
    }

    @Override
    public SharkKB getSharkKb() {
        return sharkKB;
    }

    @Override
    public List<String> getDependentImporters() {
        return Arrays.asList();
    }

    @Override
    public ImporterContext getContext() {
        return importerContext;
    }
}
