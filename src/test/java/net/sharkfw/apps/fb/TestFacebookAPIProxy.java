package net.sharkfw.apps.fb;

import net.sharkfw.apps.fb.core.cache.CachedRequestInterceptor;
import net.sharkfw.apps.fb.core.service.FacebookAccessTokenServiceProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/test-fb-importer-plugin.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFacebookAPIProxy {

	@Autowired
	protected FacebookAccessTokenServiceProvider serviceProvider;

	@Test
	public void TemplateFetchObject_fetchObjectMultipleTimes_theObjectIsFetchedOnlyOnceFromTheServer() {
		FacebookTemplate api = new FacebookTemplate("CAACEdEose0cBACTzZCgZAgxOu2woSviSZBYi2WV3A2WJ873URZAhlRMOVMIDeqgPFaIXE9jE2C2LQbhZAynI7w8zsFZBFwqdZBRo6QZAh0ElLHIpGyPUte12N2EnlwmRRkE9vp3ZBSZB9ROhA8VmRTt3UxQsqYAjlbOizPbXc4nZAj2mLCniv9tgLqEmx2x8nC6iAqKkwpxzzMtfyheer30j3fIYLZAo70P2e5QZD");
		User user = api.userOperations().getUserProfile();

	}

}
