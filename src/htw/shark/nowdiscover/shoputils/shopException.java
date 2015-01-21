package htw.shark.nowdiscover.shoputils;

public class shopException extends Exception {
	public shopException(String message) {
		super(message);
		System.err.println("ShopException has been thrown!");

	}
}
