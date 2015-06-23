package net.sharkfw.apps.fb.core.importer;

import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.knowledgeBase.SharkKB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;

import java.util.Arrays;
import java.util.List;

public abstract class BaseFBImporter implements FBImporter {

    @Autowired
    protected FacebookServiceProvider serviceProvider;

    @Autowired
    protected SharkKB sharkKB;

    @Override
    public Facebook getFacebookAPI() {
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
}
