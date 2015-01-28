package htw.shark.nowdiscover.tests;

import static org.junit.Assert.fail;
import htw.shark.nowdiscover.factory.*;
import htw.shark.nowdiscover.shoputils.*;

import org.junit.*;
/**
 * Tests for all Categories
 * 
 *
 */
public class CategoryTests {
	Shop shop;

	@Before
	public void init() {
		shop = ShopEngine.getShopEngine();
	}

	@After
	public void clean() {
		shop = null;
	}

	@Test
	public void TestCategoryGetName() throws Exception {
		Category category = shop.createCategory("music",
				"http://en.wikipedia.org/wiki/Music");
		Assert.assertEquals("music", category.getName());
	}

	@Test
	public void TestCategoryGetUrl() throws Exception {
		Category category = shop.createCategory("music",
				"http://en.wikipedia.org/wiki/Music");
		if (category.getUrls().length == 1) {
			Assert.assertEquals("http://en.wikipedia.org/wiki/Music",
					category.getUrls()[0]);
		} else {
			fail("There category should only have one url.");
		}

	}
}
