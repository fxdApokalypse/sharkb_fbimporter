package net.sharkfw.apps.fb;


import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

public class CustomMockitoAnswers {
	public static Answer<ClientHttpResponse> answerWithRequestExecute() {
		return new Answer<ClientHttpResponse>() {
			@Override
			public ClientHttpResponse answer(InvocationOnMock invocation) throws Throwable {
				HttpRequest request = invocation.getArgumentAt(0, HttpRequest.class);
				byte[] bodyInner = invocation.getArgumentAt(1, byte[].class);
				return invocation.getArgumentAt(2, ClientHttpRequestExecution.class).execute(request, bodyInner);
			}
		};
	}
}
