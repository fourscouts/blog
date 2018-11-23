package nl.fourscouts.security;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class OAuth2UserAgentUtilsTest {
	@Test
	public void shouldAddHeaders() throws URISyntaxException {
		RequestEntity<?> requestEntity = new RequestEntity<>("body", new HttpHeaders(), HttpMethod.POST, new URI("https://example.com"));

		RequestEntity<?> withUserAgent = OAuth2UserAgentUtils.withUserAgent(requestEntity);
		assertThat(withUserAgent).isNotNull();
		assertThat(withUserAgent.getBody()).isSameAs(requestEntity.getBody());
		assertThat(withUserAgent.getHeaders()).containsKey(HttpHeaders.USER_AGENT);
		assertThat(withUserAgent.getMethod()).isSameAs(requestEntity.getMethod());
		assertThat(withUserAgent.getUrl()).isSameAs(requestEntity.getUrl());
	}
}
