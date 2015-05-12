package htw.shark.nowdiscover.sandbox;

import htw.shark.nowdiscover.dataimportutils.CSV;
import htw.shark.nowdiscover.factory.ShopEngine;
import htw.shark.nowdiscover.shoputils.Category;
import htw.shark.nowdiscover.shoputils.Product;
import htw.shark.nowdiscover.shoputils.ShopException;
import htw.shark.nowdiscover.userutils.Profile;
import htw.shark.nowdiscover.userutils.Profile_Comparision;
import htw.shark.nowdiscover.userutils.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sharkfw.knowledgeBase.SharkKBException;

import com.restfb.exception.FacebookException;

public class SharkFinalExport {

	/**
	 * The Access token for the test User Atze
	 */
	private static final String TOKEN_ATZE = "CAACEdEose0cBAIH6bftmxbZBZCPbzWwXZBudAY7TnhkC6cZCDkMBeHwaTUCD2sbgAANOMkZCTwiRUxKXr20OH1p9W79WwqTVJ8RBwZAZAOpoKVugpgZB4u90z4DupWSPZBvOVwM8C3e6WtrOWudlzEsEZCNYK7BFpZAGwDpIThQJhFEI7FguYqZC8Ur90K1vZAnt2OEpaPjRsfFQbN2ki1mtNUdEiyZCW78ezpjcEZD";

	/**
	 * THe Access token for the test User Clause
	 */
	private static final String TOKEN_CLAUSE = "CAACEdEose0cBAHdWzsHriSzIwKHRs5uJvX60wJQWd3YR4hT1PF23xDl2taLkH9K3AbDC5lwOLszJCBLUsR62Wk0bUB3DFFBsANAYcP7lxN6M7B3mONVBARs2eGbWEyk8s4OWJBAEd5tYayyXfm0OZBgmbhmBSZAWeioAcBlAzi6BXpz0HqtoHDOeqsZB6zSjCldz0XTmIWEMOpEY4pLUI53HDV9TaMZD";

	public static void main(String[] args) throws SharkKBException, FacebookException {

		try {
			ShopEngine faf = (ShopEngine) ShopEngine.getShopEngine();

			User claus = faf.createUser(TOKEN_CLAUSE);
			User atze = faf.createUser(TOKEN_ATZE);

			Product pcmon = faf.createProduct("pcmonitor", "http://www.amazon.de/pcmonitor.html");
			Product printer = faf.createProduct("printer", "http://www.amazon.de/printer.html");
			Category pccategory2 = faf.createCategory("pcperipherals2", "http://www.wikipe2dia.net/peripherals_pc.html");
			pcmon.addCategories(faf.getAllCategories());
			printer.addCategories(faf.getAllCategories());

			List<Product> products = faf.getAllProducts();
			Profile atzeprofile = faf.createProfile(faf.createInterest(printer, atze, -1.0));
			Profile clausprofile = faf.createProfile(faf.createInterest(printer, claus, -1.0));

			CSV xyz = new CSV();
			faf.exportCSV();

			Profile_Comparision pc = new Profile_Comparision();
			// double compare_results = pc.compareProfiles(atzeprofile, clausprofile);
			// System.out.println("ergebnis" + compare_results);

		} catch (ShopException ex) {
			Logger.getLogger(SharkFinalExport.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
