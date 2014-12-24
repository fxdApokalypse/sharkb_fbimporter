package htw.shark.nowdiscover.sandbox;

import java.util.*;

import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.*;

/**
 * This is just a "test-class" where I play with the possibilities of product
 * recoomendations using shark. It's work in progress.
 * 
 * @author Ido Sternberg
 *
 */
public class SharkSandbox {

	public static void main(String[] args) throws SharkKBException {
		SharkKB kb = new InMemoSharkKB();
		SemanticNet catalog = kb.getTopicsAsSemanticNet();
		SNSemanticTag photoBook, camera, exhibition, ipod, photography, devices, pcMonitor;

		photoBook = catalog.createSemanticTag("Book: Portrait Photography",
				"http://youtube.com/photoBook");
		camera = catalog.createSemanticTag("Camera: SONY IS345BRG200",
				"http://youtube.com/SONYis345");
		exhibition = catalog.createSemanticTag("Exhibition: Photos of NY",
				"http://youtube.com/photony");
		ipod = catalog.createSemanticTag("Ipod: apple123ipod",
				"http://youtube.com/apple123");
		pcMonitor = catalog.createSemanticTag("PC Monitor: bzz12",
				"http://youtube.com/bzz12");
		photography = catalog.createSemanticTag("Photography",
				"http://en.wikipedia.org/wiki/Photography");
		devices = catalog.createSemanticTag("Electro Devices",
				"http://en.wikipedia.org/wiki/Devices");

		String isInTheWorldOf = "is in the world of ";

		camera.setPredicate(isInTheWorldOf, photography);
		camera.setPredicate(isInTheWorldOf, devices);
		photoBook.setPredicate(isInTheWorldOf, photography);
		exhibition.setPredicate(isInTheWorldOf, photography);
		ipod.setPredicate(isInTheWorldOf, devices);
		pcMonitor.setPredicate(isInTheWorldOf, devices);
		System.out.println("Item: " + camera.getName() + "\t["
				+ camera.getSI()[0] + "] \nis in the world(s) of: ");
		Enumeration<SNSemanticTag> cameraBelongsTo = camera
				.targetTags(isInTheWorldOf);
		while (cameraBelongsTo != null && cameraBelongsTo.hasMoreElements()) {
			SNSemanticTag aTag = cameraBelongsTo.nextElement();
			System.out.print(aTag.getName());
			System.out.println("\t\t\t[" + aTag.getSI()[0] + "]");
		}

		System.out
				.println("\nRecommandations from the worlds of Photography or Devices: ");
		Enumeration<SNSemanticTag> photoWorldTags = photography
				.sourceTags(isInTheWorldOf);
		while (photoWorldTags != null && photoWorldTags.hasMoreElements()) {
			SNSemanticTag aTag = photoWorldTags.nextElement();
			if (aTag != camera) {
				System.out.print(aTag.getName());
				System.out.println("\t[" + aTag.getSI()[0] + "]");
			}
		}
		Enumeration<SNSemanticTag> electroTags = devices
				.sourceTags(isInTheWorldOf);
		while (electroTags != null && electroTags.hasMoreElements()) {
			SNSemanticTag aTag = electroTags.nextElement();
			if (aTag != camera) {
				System.out.print(aTag.getName());
				System.out.println("\t\t[" + aTag.getSI()[0] + "]");
			}
		}

	}

}
