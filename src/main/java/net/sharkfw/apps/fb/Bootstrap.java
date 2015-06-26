package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.core.importer.BaseFBImporter;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importer.FBImporter;
import net.sharkfw.apps.fb.core.importer.ImporterContext;
import net.sharkfw.apps.fb.importers.FriendsImporter;
import net.sharkfw.knowledgeBase.SharkKB;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

import java.beans.beancontext.BeanContext;
import java.util.Arrays;

public class Bootstrap {

    protected final static String[] CONTEXT_PATH = {"classpath*:/META-INF/spring/fb-importer-plugin.xml"};

    protected GenericApplicationContext ctx;

    public Bootstrap() {
        this(null, CONTEXT_PATH);
    }

    public Bootstrap(String[] args) {
        this(args, CONTEXT_PATH);
    }

    public Bootstrap(String[] args, String[] contextPath) {
        ctx = new GenericApplicationContext();
        ctx.registerShutdownHook();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
        reader.loadBeanDefinitions(CONTEXT_PATH);
        ctx.refresh();
    }

    public void run() throws FBImportException {
        FBImporter userImporter = ctx.getBean("currentUserImporter", FBImporter.class);
        FBImporter friendImporter = ctx.getBean("friendsImporter", FriendsImporter.class);


        try {
            userImporter.performImport();
            friendImporter.performImport();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws FBImportException {
        Bootstrap bootstrap = new Bootstrap(args);
        bootstrap.run();
    }


}
