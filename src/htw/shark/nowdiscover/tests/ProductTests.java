package htw.shark.nowdiscover.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import htw.shark.nowdiscover.factory.*;
import htw.shark.nowdiscover.shoputils.*;

import java.util.*;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

import org.junit.*;
import org.mockito.*;

public class ProductTests {
	private SharkKB kb;
	private Product product;
	private Product relatedProd1;
	private Product relatedProd2;
	private List<Product> relatedProds;
	private Category relatedCat1;
	private Category relatedCat2;
	private List<Category> relatedCats;

	@Before
	public void setUp() throws Exception {
		kb = new InMemoSharkKB();
		relatedProd1 = new SharkProduct(kb, "shoe", "http://shoe.com");
		relatedProd2 = new SharkProduct(kb, "hat", "http://hat.com");
		relatedCat1 = new SharkCategory(kb, "clothes", "http://clothes.com");
		relatedCat2 = new SharkCategory(kb, "cheap", "http://cheap.com");
		relatedProds = new ArrayList<>();
		relatedCats = new ArrayList<>();
		relatedProds.add(relatedProd1);
		relatedProds.add(relatedProd2);
		relatedCats.add(relatedCat1);
		relatedCats.add(relatedCat2);
	}

	@After
	public void tearDown() throws Exception {
		product = null;
	}

	@Test
	public void testGetName() {
		product = new SharkProduct(kb, "shirt", "http://shirt.com");
		assertEquals("shirt", product.getName());
	}

	@Test
	public void testgetUrlsOneProduct() throws Exception {
		product = new SharkProduct(kb, "shirt", "http://shirt.com");
		String url = null;
		if (product.getUrls().length == 1) {
			url = product.getUrls()[0];
		} else {
			fail("There should be exactly one item in the array.");
		}
		assertEquals("http://shirt.com", url);
	}

	@Test
	public void testGetUrlMoreThanOneProduct() throws Exception {
		String[] urls = { "http://shirt.com", "http://hemd.com" };
		product = new SharkProduct(kb, "shirt", urls);
		String[] getUrls = product.getUrls();
		Assert.assertArrayEquals(urls, getUrls);
	}

	@Test
	public void testAddRelatedProducts() throws Exception {
		Shop s = ShopEngine.getShopEngine();
		product = s.createProduct("shirt", "http://shirt.com");
		// product = new SharkProduct(kb, "shirt", "http://shirt.com");
		product.addRelatedProducts(relatedProds);
		List<Product> getRelated = product.getRelatedProducts();
		// getRelatedProducts() sorts the products by name
		assertEquals(this.relatedProd1.getName(), getRelated.get(1).getName());
		assertEquals(this.relatedProd2.getName(), getRelated.get(0).getName());
	}

	@Test
	public void testName() throws Exception {

	}

	@Test
	public void testAddRelatedCategories() throws Exception {
		Shop s = ShopEngine.getShopEngine();
		product = s.createProduct("shirt", "http://shirt.com");
		product.addCategories(relatedCats);
		String catName1 = relatedCats.get(0).getName();
		// getCategories() sorts the categories by name
		assertEquals(catName1, product.getCategories().get(0).getName());
	}

	@Test
	public void testAddRelatedCategoriesMockito() throws Exception {
		CategoryMock cat = Mockito.mock(CategoryMock.class);
		// Mockito.when(cat.getCategory()).thenReturn(new C);
		fail();
	}

	@Test
	public void testNameTooShort() throws Exception {
		fail();
	}

	@Test
	public void testNameTooLong() throws Exception {
		fail();
	}
}
