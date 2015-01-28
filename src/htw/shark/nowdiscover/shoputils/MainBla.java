package htw.shark.nowdiscover.shoputils;

import htw.shark.nowdiscover.factory.*;
import net.sharkfw.system.*;

public class MainBla {

	public static void main(String[] args) {
		try {
			ShopEngine.getShopEngine().createCategory("sports", "sports.com");
		} catch (shopException e) {
			e.printStackTrace();
		} catch (SharkException e) {
			e.printStackTrace();
		}
	}
}
