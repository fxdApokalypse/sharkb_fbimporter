package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importer.FBImporter;
import net.sharkfw.apps.fb.core.importer.plan.BaseImportPlan;
import net.sharkfw.apps.fb.importers.CurrentUserImporter;
import net.sharkfw.apps.fb.importers.FriendsImporter;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.filesystem.FSSharkKB;
import net.sharkfw.knowledgeBase.sql.SQLSharkKB;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Map;

public class Bootstrap {

    protected final static String[] CONTEXT_PATH = {"classpath*:/META-INF/spring/fb-importer-plugin.xml"};
    private static boolean prop;

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
        BaseImportPlan importPlan = new BaseImportPlan();

        Map<String, FBImporter> importers =  ctx.getBeansOfType(FBImporter.class);
        importers.forEach((name, importer) -> {
            importPlan.add(importer);
        });

        try {
           importPlan.execute();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws FBImportException {
        Bootstrap bootstrap = new Bootstrap(args);
        bootstrap.run();
    }


}
