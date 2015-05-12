package htw.shark.nowdiscover.dataimportutils;

import htw.shark.nowdiscover.shoputils.Category;
import htw.shark.nowdiscover.shoputils.Product;
import htw.shark.nowdiscover.userutils.Interest;
import htw.shark.nowdiscover.userutils.Profile;
import htw.shark.nowdiscover.userutils.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exports and imports csv files that include all the raw data needed to
 * recreate a knowledgebase on another system. Useful for transfering
 * knowledgebases or reload them after a system reboot.
 * 
 * @author Alexander Ihm s0543565, Claus Holland s0542708, Jörn Sattler s0542818
 */
public class CSV implements CSV_Interface {

	private String delimiter = "§";

	private String delimiter2 = "|";

	private final String CRLF = "\r\n";

	/**
	 * Imports the csv files to recreate a knowledgebase with all the
	 * information
	 * 
	 * @param filename
	 *            is the name of the imported file
	 * @return importlist is a List Array holding the information used to
	 *         recreate the knowledgebase in the Shopengine
	 */
	@Override
	public List<List<String>> importCSV(String filename) {

		List<List<String>> importlist = new ArrayList<List<String>>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = null;
			while ((line = br.readLine()) != null) {

				List<String> stringlist = new ArrayList<String>();
				try (Scanner s = new Scanner(line)) {
					s.useDelimiter(delimiter);
					while (s.hasNext()) {
						stringlist.add(s.next());
					}
				}
				importlist.add(stringlist);

			}
		} catch (IOException ex) {
			Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
		}

		return importlist;

	}

	/**
	 * Exports the basic knowledgebase information to csv-files in the
	 * working-directory. With those files one is able to recreate a
	 * knowledge-base on another system running this programm.
	 * 
	 * @param users
	 *            contains basic user-information
	 * @param profiles
	 *            contains the interests
	 * @param products
	 *            contains the product information
	 * @param categories
	 *            contains the product-category information
	 */
	@Override
	public void exportCSV(List<User> users, List<Profile> profiles, List<Product> products, List<Category> categories) {

		generateCsvFile("customer.csv", users, profiles);
		generateCsvProductFile("products.csv", products);
		generateCsvCategoryFile("categories.csv", categories);
	}

	/**
	 * Creates the user-data and user-profile csv-File
	 * 
	 * @param sFileName
	 *            contains the filename
	 * @param users
	 *            contains basic user-information
	 * @param profiles
	 *            contains the interests
	 */
	private void generateCsvFile(String sFileName, List<User> users, List<Profile> profiles) {
		try (FileWriter writer = new FileWriter(sFileName)) {

			for (User csvuser : users) {
				writer.append(csvuser.getUserID());
				writer.append(delimiter);
				writer.append("https://www.facebook.com/app_scoped_user_id/" + csvuser.getUserID());
				writer.append(delimiter);

				for (String arraycontent : csvuser.getUserInformation()) {
					writer.append(arraycontent + delimiter);
				}

				writer.append(delimiter2);
				writer.append(delimiter);

				for (Profile csvprofile : profiles) {
					if (!csvprofile.getUserID().equals(csvuser.getUserID())) continue;

					ArrayList<Interest> interests = csvprofile.getInterest();
					Iterator<Interest> itr = interests.iterator();
					while (itr.hasNext()) {
						Interest interest = itr.next();
						writer.append(interest.getTopicname());
						writer.append(delimiter);
						writer.append(interest.getRating());
						writer.append(delimiter);
					}
				}
				writer.append(CRLF);

			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the product-csv-file
	 * 
	 * @param sFileName
	 *            contains the filename
	 * @param products
	 *            contains the basic product-information
	 */
	private void generateCsvProductFile(String sFileName, List<Product> products) {

		try (FileWriter productwriter = new FileWriter(sFileName)) {

			for (Product csvproduct : products) {

				productwriter.append(csvproduct.getName());
				productwriter.append(delimiter);

				for (String s : csvproduct.getUrls()) {

					productwriter.append(s);
					productwriter.append(delimiter);

				}

				productwriter.append(delimiter2);
				productwriter.append(delimiter);

				for (Category c : csvproduct.getCategories()) {

					productwriter.append(c.getName());
					productwriter.append(delimiter);

				}

				productwriter.append(delimiter2);
				productwriter.append(delimiter);

				for (Product p : csvproduct.getRelatedProducts()) {

					productwriter.append(p.getName());
					productwriter.append(delimiter);

				}

				productwriter.append(CRLF);

			}

			productwriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the category-file, which has to be imported first, as an
	 * environment for the products
	 * 
	 * @param sFileName
	 *            contains the filename
	 * @param categories
	 *            contains the category-tags
	 */
	private void generateCsvCategoryFile(String sFileName, List<Category> categories) {

		try (FileWriter categorywriter = new FileWriter(sFileName)) {

			for (Category csvcategory : categories) {
				categorywriter.append(csvcategory.getName());
				categorywriter.append(delimiter);

				for (String c : csvcategory.getUrls()) {

					categorywriter.append(c);
					categorywriter.append(delimiter);

				}

				categorywriter.append(delimiter2);
				categorywriter.append(delimiter);
				categorywriter.append(CRLF);
			}
			categorywriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
