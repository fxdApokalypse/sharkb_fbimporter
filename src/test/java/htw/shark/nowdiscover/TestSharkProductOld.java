package htw.shark.nowdiscover;

/**
 * 
 * 
 */
public class TestSharkProductOld {
	// SharkKB kb = new InMemoSharkKB();
	// SemanticNet sn;
	// SharkProduct testProduct;
	// SNSemanticTag productTag1;
	// SNSemanticTag productTag2;
	// SNSemanticTag productTag3;
	// List<Product> related;
	// SharkProduct related1;
	// SharkProduct related2;
	// private SNSemanticTag cat1, cat2;
	// List<Category> cl;
	// SharkCategory sc1, sc2;
	//
	// @Before
	// public void init() throws SharkKBException {
	// sn = kb.getTopicsAsSemanticNet();
	// productTag1 = sn.createSemanticTag("Marshal Amp", "youtube.com/amp13");
	// productTag2 = sn.createSemanticTag("Slash DVD", "youtube.com/slashdvd");
	// productTag3 = sn.createSemanticTag("Guitar Strings",
	// "youtube.com/strings");
	// related1 = new SharkProduct(productTag1);
	// related2 = new SharkProduct(productTag2);
	// testProduct = new SharkProduct(productTag3);
	// related = new ArrayList();
	// related.add(related1);
	// related.add(related2);
	// sc1 = new SharkCategory();
	// sc2 = new SharkCategory();
	// sc1.setCategory("Rock 'N Roll", "http://rnr.de", "http://rock.ru");
	// sc2.setCategory("Music", "http://music.de");
	// cl = new ArrayList();
	// cl.add(sc1);
	// cl.add(sc2);
	//
	// }
	//
	// @After
	// public void clean() {
	// this.testProduct = null;
	// }
	//
	// @Test
	// public void testAddRelatedProduct() throws Exception {
	// testProduct = new SharkProduct(productTag3);
	// testProduct.addRelatedProducts(related);
	// SharkProduct sp1 = (SharkProduct) testProduct.getRelatedProducts().get(
	// 0);
	// SharkProduct sp2 = (SharkProduct) testProduct.getRelatedProducts().get(
	// 1);
	// System.out.println();
	// System.out.println("----Add and get related products----");
	// System.out.print("Product: " + testProduct.getProductTag().getName());
	// System.out.println(" [Video:" + testProduct.getProductTag().getSI()[0]
	// + "]");
	// System.out.print("Related: " + sp1.getProductTag().getName());
	// System.out.println(" \t[Video:" + sp1.getProductTag().getSI()[0] + "]");
	// System.out.print("Related: " + sp2.getProductTag().getName());
	// System.out.println(" \t[Video:" + sp2.getProductTag().getSI()[0] + "]");
	// System.out.println("-------------------------------------");
	// // fails sometimes, bcoz sorting is random
	// assertEquals("Marshal Amp", sp1.getProductTag().getName());
	// assertEquals("Slash DVD", sp2.getProductTag().getName());
	//
	// }
	//
	// @Test
	// public void testAddCategories() throws Exception {
	// this.testProduct = new SharkProduct(productTag1);
	// this.testProduct.addCategories(cl);
	// List<Category> cat = this.testProduct.getCategories();
	// System.out.println("----Add and get product categories ----");
	// System.out.print("Product: "
	// + this.testProduct.getProductTag().getName());
	// System.out.println(" \t[Video:"
	// + testProduct.getProductTag().getSI()[0] + "]");
	// System.out.print("Category: " + cat.get(0).getCategoryName());
	// System.out.println(" \t[Video:" + cat.get(0).getUrls()[0] + "]");
	// System.out.print("Category: " + cat.get(1).getCategoryName());
	// System.out.print(" \t[Video:" + cat.get(1).getUrls()[0]);
	// System.out.println(", " + cat.get(1).getUrls()[1] + "]");
	// assertEquals("Music", cat.get(0).getCategoryName());
	// assertEquals("Rock 'N Roll", cat.get(1).getCategoryName());
	// }
	//
	// @Test
	// public void testGetProductName() throws Exception {
	// SNSemanticTag tag = sn.createSemanticTag("Cake", "youtube.com/cake");
	// this.testProduct = new SharkProduct(tag);
	// assertEquals("Cake", this.testProduct.getProductName());
	// }
}
