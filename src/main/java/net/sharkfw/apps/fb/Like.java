package net.sharkfw.apps.fb;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;

public class Like extends FacebookType {
	
	private static final long serialVersionUID = -4728252352287149836L;
	
	@Facebook
	private String name;
	
	@Facebook
	private String link;
	
	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}