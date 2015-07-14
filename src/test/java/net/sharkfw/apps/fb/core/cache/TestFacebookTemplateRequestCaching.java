package net.sharkfw.apps.fb.core.cache;

import net.sharkfw.apps.fb.BaseFBImporterTests;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import java.io.IOException;

import static net.sharkfw.apps.fb.CustomMockitoAnswers.answerWithRequestExecute;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class TestFacebookTemplateRequestCaching extends BaseFBImporterTests {

	@Test
	public void TemplateFetchObject_requestObjectsMultipleTimes_theObjectIsFetchedOnlyOnceFromTheServer() throws IOException {
		mockServer.expect(requestTo(getFBUrl("me", UserOperations.PROFILE_FIELDS)))
				.andExpect(method(HttpMethod.GET))
				.andRespond(testResponse("me"));

		ClientHttpRequestInterceptor mockInterceptor = mock(ClientHttpRequestInterceptor.class);
		when(mockInterceptor.intercept(any(), any(), any())).then(answerWithRequestExecute());

		FacebookTemplate api = getFBApi();
		api.getRestTemplate().getInterceptors().add(mockInterceptor);

		User expectedUser = getTestJSONObject("me", User.class);
		for (int i = 0; i < 3; i++) {
			User actualUser = api.userOperations().getUserProfile();
			assertEquals(expectedUser.getId(), actualUser.getId());
			assertEquals(expectedUser.getName(), actualUser.getName());
		}

		verify(mockInterceptor, times(1)).intercept(any(), any(), any());

	}



}
