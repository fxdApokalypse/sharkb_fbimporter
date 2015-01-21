package htw.shark.nowdiscover.sandbox;

import htw.shark.nowdiscover.*;

import java.io.*;
import java.util.*;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;
import net.sharkfw.system.*;

public class ImportCSV {
	SharkKB kb;
	SemanticNet sn;
	List<Product> products; // create a catalog instead.

	public static void main(String[] args) {
		ImportCSV obj = new ImportCSV();
		try {
			obj.run();
		} catch (SharkKBException e) {
			e.printStackTrace();
		}
	}

	public void run() throws SharkKBException {
		String csvFile = "\\Users\\Sterni\\workspace\\SharkNowdiscover\\resources\\products.csv";
		String line = "";
		String csvSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile));) {
			kb = new InMemoSharkKB();
			sn = kb.getTopicsAsSemanticNet();
			int counter = 0;
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] imports = line.split(csvSplitBy);
				SNSemanticTag tmp = sn
						.createSemanticTag(imports[0], imports[1]);
				Product p = new SharkProduct(tmp.getName(), tmp.getSI());
				products = new ArrayList<Product>();
				products.add(p);
				System.out.println(products.get(0).getName());
				counter++;
				// if (imports.length > 2) {
				// for (String value : imports) {
				// // add category
				// }
				// }
				// sn.add(tmp);
			}

		} catch (IOException | SharkKBException e) {
			e.printStackTrace();
		}
		System.out.println(L.kb2String(kb));
		System.out.println("Done");
	}
}
