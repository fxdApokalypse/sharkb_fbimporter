/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.sandbox;

import htw.shark.nowdiscover.factory.ShopEngine;
import htw.shark.nowdiscover.shoputils.ShopException;
import htw.shark.nowdiscover.userutils.Profile;
import htw.shark.nowdiscover.userutils.Profile_Comparision;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sharkfw.knowledgeBase.SharkKBException;

import com.restfb.exception.FacebookException;
/**
 *
 * @author Jörn Sattler s0542818, Claus Holland s0542708, Alexander Ihm s0543565
 */

public class SharkFinalImport {

	public static void main(String[] args) throws SharkKBException, FacebookException {
		ShopEngine faf = (ShopEngine) ShopEngine.getShopEngine();
		try {

			System.out.println();
			faf.importproductCSV("categories.csv", "products.csv");
			faf.importuserCSV("customer.csv");

			for (int i = 0; i <= 1; i++) {
				for (String oink : faf.getAllUsers().get(i).getUserInformation()) { // get(1).getUserInformation())
					System.out.println(oink);

				}
			}
		} catch (ShopException ex) {
			Logger.getLogger(SharkFinalImport.class.getName()).log(Level.SEVERE, null, ex);
		}
		List<Profile> profiles = faf.getAllProfiles();
		for (Profile profile : profiles) {
			System.out.println(profile.getInterest().get(0).getTopicname());
		}

		Profile_Comparision pc = new Profile_Comparision();
		double compare_result = pc.compareProfiles(profiles.get(0), profiles.get(1));
		System.out.println("Result of the comparision: " + compare_result);

	}

}
