package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.knowledgeBase.SharkKB;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/test-fb-importer-plugin.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseFBImporterTests implements ApplicationContextAware {

    /**
     * The ServiceProvider for the facebook api.
     */
    @Autowired
    protected FacebookServiceProvider fbServiceProvider;

    /**
     * The test SharkKB
     */
    @Autowired
    protected SharkKB testSharkKB;

    /**
     * The Mock for the facebook api
     */
    protected MockRestServiceServer mockServer;

    /**
     * The Spring Context for this test
     */
    protected ApplicationContext ctx;

    /**
     * Create a RestMockServer which can be obtained by the field {@link #mockServer}.
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
        return withSuccess(new ClassPathResource("test-data/" + testData + ".json", getClass()), MediaType.APPLICATION_JSON);
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
     * Retrieves the test shark kb which is
     * recreate after each unit test.
     *
     * @return the shark kb which is used for this test.
     */
    public SharkKB getKB() {
        return testSharkKB;
    }

    /**
     * Retrieves the {@link ApplicationContext} of this test.
     *
     * @return the context.
     */
    public ApplicationContext getContext() {
       return this.ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
