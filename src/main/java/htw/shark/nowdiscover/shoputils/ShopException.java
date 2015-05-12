package htw.shark.nowdiscover.shoputils;

public class ShopException extends Exception {

	private static final long	serialVersionUID	= 1L;

	protected ShopException() {
		super();
	}

	public ShopException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ShopException(Throwable arg0) {
		super(arg0);
	}

	public ShopException(String message) {
		super(message);

	}
}
