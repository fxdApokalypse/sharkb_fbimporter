package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.core.importer.ImporterContext;
import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.impl.json.FacebookModule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * A base test class for testing classes which are depends
 * on the Facebook REST Web Service.
 *
 * This class provides access to a mocked RestServer,
 * and provides convenient access to:
 * <ul>
 *     <li>Test Data</li>
 *     <li>SharkKB</li>
 *     <li>Facebook API</li>
 *     <li>Spring Application Context</li>
 * </ul>
 */
public abstract class FBImporterTest extends BaseTest {

    /**
     * The ServiceProvider for the facebook api.
     */
    @Autowired
    private FacebookServiceProvider fbServiceProvider;

    @Autowired
    private ImporterContext importerContext;

    /**
     * The Mock for the facebook api
     */
    protected MockRestServiceServer mockServer;


    /**
     * Initialize the base test.
     */
    public FBImporterTest() {
        super();

    }

    /**
     * Create a RestMockServer which can be obtained by the field {@link #mockServer}.
     * This mock server mocks the facebook rest api in order to
     * test the import without accessing the facebook api through the rest interface.
     */
    @Before
    public void createMockServer() {
        mockServer = MockRestServiceServer.createServer(fbServiceProvider.getApi().getRestTemplate());
    }


    /**
     * Retrieves a response json stub for a corresponding test data.
     *
     * @param testData the name of the test-data
     *
     * @return the response stub
     */
    public DefaultResponseCreator testResponse(String testData) {
        return withSuccess(getTestResource(testData), MediaType.APPLICATION_JSON);
    }


    /**
     * Builds url to the facebook api.
     *
     * @param path the path of the desired resource.
     * @param fields the desired fields of the desired resource.
     *
     * @return the url to the desired URL.
     */
    public String getFBUrl(String path, String ...fields) {
        StringBuilder url = new StringBuilder();
        url.append(GraphApi.GRAPH_API_URL).append(path);

        if (fields.length > 0) {
            url.append("?fields=");
            url.append(Arrays.stream(fields).collect(Collectors.joining("%2C")));
        }

        return url.toString();
    }

    /**
     * The Facebook API Template which encapsulates the access to the fb rest service
     * in object oriented manner.
     *
     * @return the fb api template.
     */
    public FacebookTemplate getFBApi() {
        return fbServiceProvider.getApi();
    }

    /**
     * Returns the import context for testing.
     *
     * @return the import context.
     */
    public ImporterContext getImportContext() {
        return this.importerContext;
    }
}
