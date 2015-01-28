package htw.shark.nowdiscover.tests;

import static org.junit.Assert.assertEquals;
import htw.shark.nowdiscover.helpers.*;
import htw.shark.nowdiscover.shoputils.*;
import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

import org.junit.*;

public class CategoryTests {
	SharkKB kb;
	Category category;

	@Before
	public void setUp() throws Exception {
		kb = new InMemoSharkKB();

	}

	@After
	public void tearDown() throws Exception {
		category = null;
	}

	@Test
	public void testConstructor() throws SharkKBException {
		category = new SharkCategory(kb, "Sports", "http://sport.com");
		assertEquals("Sports", category.getName());
	}

	@Test
	public void testSetName() throws SharkKBException {
		category = new SharkCategory(kb, "Sports", "http://sport.com");
		category.setName("Food");
		assertEquals("Food", category.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameTooShort() throws Exception {
		category = new SharkCategory(kb, "foo", "http://foo.com");
		category.setName(StringHelper.randomString(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameTooShortInConstructor() throws Exception {
		category = new SharkCategory(kb, StringHelper.randomString(1),
				"http://null.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameTooLong() throws Exception {
		category = new SharkCategory(kb, "foo", "http://null.com");
		category.setName(StringHelper.randomString(21));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameTooLongInConstructor() throws Exception {
		category = new SharkCategory(kb, StringHelper.randomString(21),
				"http://null.com");
	}

	@Test
	public void testAddUrl() throws SharkKBException {
		category = new SharkCategory(kb, "Sports", "http://sport.com");
		category.addUrls("http://foo.com");
		assertEquals("http://foo.com", category.getUrls()[1]);
	}

	@Test
	public void testRemoveUrls() throws SharkKBException {
		category = new SharkCategory(kb, "Sports", "http://sport.com");
		category.addUrls("http://foo.com");
		category.addUrls("http://bar.com");
		category.removeUrls(new String[] { "http://sport.com", "http://foo.com" });
		assertEquals("http://bar.com", category.getUrls()[0]);
	}

}
