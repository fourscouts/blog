/*
 * MIT License
 *
 * Copyright (c) 2018 FourScouts B.V.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package nl.fourscouts.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestOperations;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import static nl.fourscouts.security.OAuth2TestData.ACCESS_TOKEN;
import static nl.fourscouts.security.OAuth2TestData.USERNAME_ATTRIBUTE_NAME;
import static nl.fourscouts.security.OAuth2TestData.USER_INFO_URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;

@RunWith(MockitoJUnitRunner.class)
public class RestOAuth2UserServiceTest {
	@Mock
	private RestOperations restOperations;

	@InjectMocks
	private RestOAuth2UserService service;

	@Captor
	private ArgumentCaptor<HttpEntity<Map<String, Object>>> requestCaptor;

	@Test
	public void shouldGetUserInfo() {
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, ACCESS_TOKEN, Instant.now(), Instant.now().plusSeconds(10));

		ParameterizedTypeReference<Map<String, Object>> type = new ParameterizedTypeReference<Map<String, Object>>() {};

		when(restOperations.exchange(eq(USER_INFO_URI), eq(GET), requestCaptor.capture(), eq(type)))
			.thenReturn(ResponseEntity.ok(Collections.singletonMap(USERNAME_ATTRIBUTE_NAME, "Scout")));

		OAuth2User oAuth2User = service.loadUser(new OAuth2UserRequest(OAuth2TestData.testClient(), accessToken));

		HttpEntity<Map<String, Object>> requestEntity = requestCaptor.getValue();
		assertThat(requestEntity.getHeaders()).isNotNull();
		assertThat(requestEntity.getHeaders()).containsKey(USER_AGENT);
		assertThat(requestEntity.getHeaders()).containsEntry(AUTHORIZATION, Collections.singletonList("Bearer accessToken"));

		assertThat(oAuth2User).isNotNull();
		assertThat(oAuth2User.getAuthorities()).hasSize(1);
		assertThat(oAuth2User.getAttributes()).containsEntry(USERNAME_ATTRIBUTE_NAME, "Scout");
	}
}