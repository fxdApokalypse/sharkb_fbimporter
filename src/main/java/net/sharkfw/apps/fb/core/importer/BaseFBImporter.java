package net.sharkfw.apps.fb.core.importer;

import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.knowledgeBase.SharkKB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class BaseFBImporter implements FBImporter {

    @Autowired
    protected FacebookServiceProvider serviceProvider;

    @Autowired
    protected  ImporterContext importerContext;

    @Autowired
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
