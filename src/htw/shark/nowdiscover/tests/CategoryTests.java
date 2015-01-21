package htw.shark.nowdiscover.tests;

import static org.junit.Assert.assertEquals;
import htw.shark.nowdiscover.shoputils.*;
import net.sharkfw.knowledgeBase.*;

import org.junit.*;

public class CategoryTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetName() throws SharkKBException {
		Category category;
		category = new SharkCategory(null, null, null);
		category.setName("Sports");
		assertEquals("Sports", category.getName());
	}

}
