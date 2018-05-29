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

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class OAuth2TestData {
	private OAuth2TestData() {}

	public static final String CLIENT_ID = "clientId";
	public static final String CLIENT_SECRET = "clientSecret";
	public static final String AUTHORIZATION_URI = "http://example.com/authorization";
	public static final String TOKEN_URI = "http://example.com/token";
	public static final String REDIRECT_URI = "http://example.com/redirect";
	public static final String USER_INFO_URI = "http://example.com/user";
	public static final String USERNAME_ATTRIBUTE_NAME = "username";
	public static final String SCOPE = "identity";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";


	public static ClientRegistration testClient() {
		return ClientRegistration.withRegistrationId("testClient")
			.clientName("Test Client")
			.clientId(CLIENT_ID)
			.clientSecret(CLIENT_SECRET)
			.authorizationUri(AUTHORIZATION_URI)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.tokenUri(TOKEN_URI)
			.userInfoUri(USER_INFO_URI)
			.userNameAttributeName(USERNAME_ATTRIBUTE_NAME)
			.redirectUriTemplate(REDIRECT_URI)
			.scope(SCOPE)
			.build();
	}
}
