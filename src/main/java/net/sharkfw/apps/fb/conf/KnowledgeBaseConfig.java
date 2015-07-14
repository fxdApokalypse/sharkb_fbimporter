package net.sharkfw.apps.fb.conf;


import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.filesystem.FSSharkKB;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.knowledgeBase.sql.SQLSharkKB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * A Spring Bean Configuration for the SharkKB,
 * which should receive the imports from Facebook.
 *
 * @see http://docs.spring.io/spring-framework/docs/4.0.4.RELEASE/javadoc-api/org/springframework/context/annotation/Configuration.html
 */
@Configuration
public class KnowledgeBaseConfig {

	/**
	 * Property which defines which {@link SharkKB} implementation
	 * should be used by the importer.
	 * The following implementations are supported by the importer.
	 * <ul>
	 *     <li>InMemoSharkKB</li>
	 *     <li>FSSharkKB</li>
	 *     <li>SQLSharkKB</li>
	 * </ul>
	 */
	public static final String SHARK_KB_PROPTERTY = "sharkKB";


	/**
	 * The <code>sharkKB</code> value for the InMemoSharkKB implementation
	 * of the {@link SharkKB}.
	 */
	public static final String IN_MEMO_SHARK_KB_IMPL = "InMemoSharkKB";

	/**
	 * The <code>sharkKB</code> value for the FSSharkKB implementation
	 * of the {@link SharkKB}.
	 */
	public static final String FSSHARK_KB_IMPL = "FSSharkKB";

	/**
	 * Property which defines the root folder of a FSSharkKB.
	 */
	public static final String ROOT_FOLDER_PROPERTY = "sharkKB.file.rootFolder";

	/**
	 * The <code>sharkKB</code> value for the SQLSharkKB implementation
	 * of the {@link SharkKB}.
	 */
	public static final String SQLSHARK_KB_IMPL = "SQLSharkKB";

	/**
	 * Property which defines the connection url of a  SQLSharkKB.
	 */
	public static final String DB_URL_PROPERTY = "sharkKB.db.connectionURL";

	/**
	 * Property which defines the database user of a SQLSharkKB.
	 */
	public static final String DB_USER_PROPERTY = "sharkKB.db.user";

	/**
	 * Property which defines the database password of a SQLSharkKB.
	 */
	public static final String DB_PWD_PROPERTY = "sharkKB.db.pwd";


	@Autowired
	private Environment env;

	@Bean
	public SharkKB sharkKB() throws SharkKBException {
		String sharkKB = env.getProperty(SHARK_KB_PROPTERTY, IN_MEMO_SHARK_KB_IMPL);
		switch (sharkKB) {
			case IN_MEMO_SHARK_KB_IMPL:
				return InMemoSharkKB();
			case FSSHARK_KB_IMPL:
				return  FSSharkKB();
			case SQLSHARK_KB_IMPL:
				return SQLSharkKB();
		}
		return null;
	}

	@Bean
	@Lazy
	public SharkKB InMemoSharkKB() throws SharkKBException {
		return new InMemoSharkKB();
	}

	@Bean
	@Lazy
	public SharkKB FSSharkKB() throws SharkKBException {
		String rootFolder = env.getRequiredProperty(ROOT_FOLDER_PROPERTY);
		return new FSSharkKB(rootFolder);
	}

	@Bean(destroyMethod = "close")
	@Lazy
	public SharkKB SQLSharkKB() throws SharkKBException {
		String connectionURL = env.getRequiredProperty(DB_URL_PROPERTY);
		String user = env.getRequiredProperty(DB_USER_PROPERTY);
		String pwd = env.getRequiredProperty(DB_PWD_PROPERTY);
		return new SQLSharkKB(connectionURL, user, pwd);
	}
}
