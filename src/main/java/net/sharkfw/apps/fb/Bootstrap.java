package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.conf.AppConfig;
import net.sharkfw.apps.fb.conf.ConfigurationException;
import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importplan.ImportPlan;
import net.sharkfw.apps.fb.core.importplan.PermissionAwareImportPlan;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.social.RejectedAuthorizationException;
import org.springframework.social.ResourceNotFoundException;


/**
 * Main entry point for the facebook importer.
 */
public class Bootstrap {

    /**
     * Class Logger
     */
    private final static Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * <p>
     * The path to a spring bean configuration which acts as plugin for any new facebook import steps
     * which could be provided by a jar on the classpath.
     * </p>
     *
     * <p>
     * This jar file must contain the classes of the import steps and the classpath resource
     * classpath*:/META-INF/spring/fb-importer-plugin.xml which looks like this:
     * </p>
     *
     * <pre>{@code
     *  &lt;?xml version="1.0" encoding="UTF-8"?&gt:
     *  <beans xmlns="http://www.springframework.org/schema/beans"
     *     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *     xmlns:context="http://www.springframework.org/schema/context"
     *     xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
     *     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
     *      <context:component-scan base-package="PACKAGE.TO.YOUR.IMPORTER.CLASSES"/>
     *    </beans>
     * }</pre>
     *
     */
    public final static String[] CONTEXT_PATH = {"classpath*:/META-INF/spring/fb-importer-plugin.xml"};

    private AnnotationConfigApplicationContext ctx;

    /**
     * Creates a Bootstrap with default arguments.
     */
    public Bootstrap() {
        this(null);
    }

    /**
     * Creates Bootstrap with program arguments
     *
     * @param args the application arguments.
     */
    public Bootstrap(String[] args) {
        ctx = new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
        reader.loadBeanDefinitions(CONTEXT_PATH);
        ctx.register(AppConfig.class);
        ctx.registerShutdownHook();
        ctx.refresh();
    }

    /**
     * Run the Bootstrap which triggers the execution of all importers.
     *
     * @throws FBImportException if a import error occurred
     */
    public void run() throws FBImportException {
        setupLogLevel();
        ImportPlan importPlan = ctx.getBean(PermissionAwareImportPlan.class);
        try {
           importPlan.execute();
        } catch(Exception ex) {
            if (ex instanceof RejectedAuthorizationException || ex instanceof ResourceNotFoundException) {
                LOG.error(ex.getMessage() + ": Please update your access token inside the conf/conf.properties file.");
                System.out.println(FacebookLogin.invokeLogin());

            } else {
                ex.printStackTrace();
            }
        } finally {
            ctx.destroy();
        }
    }

    private  void setupLogLevel() {
        String logLevel = ctx.getEnvironment().getProperty("log.level");

        /* If the log.level properties is missing fallback to the default
           log level which is specified inside the classpath:/resources/log4j2.xml
           config file.
         */
        if ( logLevel != null ) {
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();

            LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
            Level level = Level.getLevel(logLevel.toUpperCase());
            if (level == null) {
                throw new ConfigurationException("Invalid configuration log.level = " + logLevel + " isn't a valid log level");
            }
            loggerConfig.setLevel(level);
            ctx.updateLoggers();
        }
    }

    /**
     * The main program of the facebook importer.
     *
     * @param args the program arguments
     * @throws FBImportException if a import error occurred
     */
    public static void main(String[] args) throws FBImportException {
        Bootstrap bootstrap = new Bootstrap(args);
        bootstrap.run();
    }


}
