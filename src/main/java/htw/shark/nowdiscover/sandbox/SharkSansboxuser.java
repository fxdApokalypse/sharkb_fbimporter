package htw.shark.nowdiscover.sandbox;

import htw.shark.nowdiscover.dataimportutils.CSV;
import htw.shark.nowdiscover.factory.ShopEngine;
import htw.shark.nowdiscover.shoputils.Product;
import htw.shark.nowdiscover.shoputils.ShopException;
import htw.shark.nowdiscover.userutils.User;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sharkfw.knowledgeBase.SharkKBException;

import com.restfb.exception.FacebookException;

/**
 *
 * @author Holland
 */

public class SharkSansboxuser {

	public static void main(String[] args) throws SharkKBException, FacebookException {

		try {
			ShopEngine faf = (ShopEngine) ShopEngine.getShopEngine();

			User claus =
			        faf.createUser("CAACEdEose0cBAJunIWvcJhHs1N4PbPSCAZBSEmfXijafeKyak3AlAvtUB4pNdq1ZCZB1ZABtEF9gBjWEwUW3VtB3Eh2BUHJURU3XuDOAKJqCL6s2wqjV10x7CgsQW4jITzK4ZCN49xxhZCtnAtcbb4dnMDBSkVy01nqjFuZBwKX1DBO7nIGV599ZBXuM8ZCiSFl5SCq0cNYCLxCCR8KVUD41WyePEsmBjKC0ZDoQ5YZD");
			User atze =
			        faf.createUser("CAACEdEose0cBAJIqqVEAbko896lf3elWYEZCVlBEQTUAwor5GwKyPsPT0cEZCOEa7Bo3YgqlNjSwAPZBpYojYmvNsvDvNEuEqIhMt5eZA31hhGRz7bOiOyBagfZCuPz7mm1IdDpRIYoga6dLZAwS3YjhzT9PpZCqej7n0q3IPfJX3oVVvilzNVt8RzFcoIm6Tfb2kC4efNZB0wlMGd17WZArD");
			Product pcmon2 =
			        faf.createProduct("pcmonitor", "http://www.amazon.de/geilermonitor2.html");
			faf.createProfile(faf.createInterest(pcmon2, atze, 1));
			CSV xyz = new CSV();

			faf.exportCSV();
			faf.importuserCSV("customer.csv");
			System.out.println();
			for (String oink : faf.getAllUsers().get(1).getUserInformation()) {
				System.out.println(oink);
			}

		} catch (ShopException ex) {
			Logger.getLogger(SharkSansboxuser.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
