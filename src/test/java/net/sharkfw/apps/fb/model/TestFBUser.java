package net.sharkfw.apps.fb.model;


import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

import org.junit.Test;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

public class TestFBUser {

	@Test(expected = IllegalArgumentException.class)
	public void create_withNullUserOrNullSharkKB_IllegalArgumentExceptionIsThrown() throws SharkKBException {
		FBUser.create(null, null);
	}
	
	@Test()
	public void create_Import() throws SharkKBException {
		
		String accessToken = "CAACEdEose0cBADNfxy1FwDe2javBNDhflmuJVtZCGKgIHxTRL6XF4lYnDLzoALcY9ZAaX2fJ7ZALko0SxKfxZAr7UJJOzg91efXBgJ88HZCAzGOffqczXL8eCbaXgNAMesPYVevQcBpmZBbfjzo4ZAu05FSjA0C53hHuyOKzp0bOgIBoZBF0Bb4BjWWzrwJPhdMgpZBWNhPmZBZB0OUvZCXSDfKptoGzvb9z6akZD";
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_3);
		User user = fbClient.fetchObject("me", User.class);
		FBUser.create(user, new InMemoSharkKB());
	}
}
