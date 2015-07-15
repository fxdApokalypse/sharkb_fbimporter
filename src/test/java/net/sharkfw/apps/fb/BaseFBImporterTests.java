package net.sharkfw.apps.fb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sharkfw.apps.fb.conf.AppConfig;
import net.sharkfw.apps.fb.core.service.FacebookServiceProvider;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseFBImporterTests.TestConfig.class, loader=AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseFBImporterTests implements ApplicationContextAware {

    @Configuration
    @Import(value = AppConfig.class)
    @PropertySource(value = "conf/test.properties")
    static class TestConfig {
        @Bean
        SharkKB sharkKB() {
            return new InMemoSharkKB();
        }
    }

    /**
     * This class is used to overcome a limitation in the {@link MockRestServiceServer}
     * where only one request to a resource is possible, because after the first request
     * the resource is closed and so multiple requests to the same resources were not possible.
     *
     * This class resets the stream on close in order to overcome the
     * above desribed problem, so that the stream is reusable by the {@link MockRestServiceServer}
     */
    class ResetOnCloseClassPathResource extends ClassPathResource {
        private byte[] buffer;
        public ResetOnCloseClassPathResource(String path) {
            super(path);
        }

        @Override
        public InputStream getInputStream() throws IOException {
            buffer = StreamUtils.copyToByteArray(super.getInputStream());
            return  new ByteArrayInputStream(buffer) {
                @Override
                public void close() throws IOException {
                    super.close();
                    pos = 0;
                }
            };
        }
    }

    /**
     * The ServiceProvider for the facebook api.
     */
    @Autowired
    private FacebookServiceProvider fbServiceProvider;

    /**
     * The test SharkKB
     */
    @Autowired
    @Qualifier("sharkKB")
    private SharkKB sharkKB;

    /**
     * The Mock for the facebook api
     */
    protected MockRestServiceServer mockServer;

    /**
     * The Spring Context for this test
     */
    private ApplicationContext ctx;

    /**
     * The Mapper which is used for creating test objects
     * from json files.
     */
    private ObjectMapper jsonMapper;


    /**
     * Initialize the base test.
     */
    public BaseFBImporterTests() {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new FacebookModule());
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
     * <p>
     *     Maps a JSON test data into a Java Object for a specific type.
     *     The JSON test
     *</p>
     *
     * @param testData the name of the JSON test file but
     *                 the .json extension must be omitted.
     * @param type the type of the returning object,
     *             could be any {@link org.springframework.social.facebook.api Facebook Type}
     * @param <T> Describes the expected type of the returning object and
     *            is required to support generic type safety.
     *            A caller should ignore this Generic Type parameter because
     *            it is set automatically by the compiler if the type <code>Class<T></code>
     *            is provided by the caller.
     * @return the mapped java object or null if the JSON test file could not be mapped to a java object.
     */
    @SuppressWarnings("unchecked")
    public <T> T getTestJSONObject(String testData, Class<T> type) {
        ClassPathResource jsonResource = getTestResource(testData);
        try (InputStream in = jsonResource.getInputStream()) {
            return jsonMapper.readValue(in, type);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Maps the JSON node called "data" from the test JSON test file
     * into a List of a specific type. The node "data" must be
     * a JSON array which is mappable to the specified element type.</p>
     *
     * @param testData the name of the JSON test file but the
     *                 .json extension must be omitted.
     * @param elementType the element type of the resulting list
     *                    could be any {@link org.springframework.social.facebook.api Facebook Type}
     * @param <T>  Describes the expected type of the returning list object and
     *             is required to support generic type safety.
     *             A caller should ignore this Generic Type parameter because
     *             it is set automatically by the compiler if the type <code>Class<T></code>
     *             is provided by the caller.
     * @return the mapped List<T> object or null if the test file could not be mapped to a List<T> object.
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getTestJSONList(String testData, Class<T> elementType) {
        JsonNode object = getTestJSONObject(testData, JsonNode.class);
        if (object != null && object.has("data")) {
            JsonNode dataNode = object.get("data");
            T[] elementArray = (T[]) Array.newInstance(elementType, 0);
            try {
                return (List<T>) Arrays.asList(jsonMapper.readValue(dataNode.toString(), elementArray.getClass()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }



    public ClassPathResource getTestResource(String testData) {
        ClassPathResource res =  new ResetOnCloseClassPathResource("/test-data/" + testData + ".json");
        return res;
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
        return sharkKB;
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
     * The Mapper which is used for creating test objects
     * from json files.
     */
    public ObjectMapper getJSONMapper() {
        return this.jsonMapper;
    }
}
