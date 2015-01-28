package htw.shark.nowdiscover.helpers;

import java.util.*;

public class StringHelper {
	public static String randomString(int length) {
		String characters = "abcdefghijklmop";
		Random rng = new Random();
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static void main(String[] args) {
		System.out.println(randomString(10));
	}
}
