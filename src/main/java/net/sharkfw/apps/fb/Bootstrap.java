package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.conf.AppConfig;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importer.FBImporter;
import net.sharkfw.apps.fb.core.importer.plan.BaseImportPlan;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class Bootstrap {

    protected final static String[] CONTEXT_PATH = {"classpath*:/META-INF/spring/fb-importer-plugin.xml"};

    protected AnnotationConfigApplicationContext ctx;

    public Bootstrap() {
        this(null);
    }

    public Bootstrap(String[] args) {
        ctx = new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
        reader.loadBeanDefinitions(CONTEXT_PATH);
        ctx.register(AppConfig.class);
        ctx.registerShutdownHook();
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
