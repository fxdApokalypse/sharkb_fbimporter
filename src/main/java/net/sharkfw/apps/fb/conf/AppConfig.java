package net.sharkfw.apps.fb.conf;

import net.sharkfw.apps.fb.core.importer.ImporterContext;
import net.sharkfw.apps.fb.core.service.FacebookAccessTokenServiceProvider;
import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * A Spring Bean Configuration for the Importer.
 *
 * @see http://docs.spring.io/spring-framework/docs/4.0.4.RELEASE/javadoc-api/org/springframework/context/annotation/Configuration.html
 */
@Configuration
@ComponentScan(basePackages = "net.sharkfw.apps.fb.importers")
@PropertySource(value = "file:${user.dir}/conf/conf.properties")
@Import(KnowledgeBaseConfig.class)
public class AppConfig {

	/**
	 * A property which defines the to used facebook accessToken
	 */
	public static final String FACEBOOK_ACCESS_TOKEN_PROPERTY = "facebook.accessToken";

	/**
	 * A property which defines the name of the underlying facebook app.
	 */
	public static final String FACEBOOK_APP_NAMESPACE_PROPERTY = "facebook.appNamespace";

	@Autowired
	Environment env;

	@Bean
	public ImporterContext importerContext() {
		return new ImporterContext();
	}

	@Bean
	public FacebookServiceProvider fbServiceProvider() {
		String accessToken = env.getRequiredProperty(FACEBOOK_ACCESS_TOKEN_PROPERTY);
		String appNamespace = env.getProperty(FACEBOOK_APP_NAMESPACE_PROPERTY, "SharkFBImporter");
		return new FacebookAccessTokenServiceProvider(accessToken, appNamespace);
	}
}


