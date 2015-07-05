package net.sharkfw.apps.fb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.social.facebook.api.impl.json.FacebookModule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
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
     * The Mapper which is used for creating test objects
     * from json files.
     */
    protected ObjectMapper jsonMapper;

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
        return withSuccess(getTestResponse(testData), MediaType.APPLICATION_JSON);
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
        ClassPathResource jsonResource = getTestResponse(testData);
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



    private ClassPathResource getTestResponse(String testData) {
        return new ClassPathResource("test-data/" + testData + ".json", getClass());
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
