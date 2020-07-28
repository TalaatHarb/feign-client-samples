package net.talaatharb.samples.serviceproject.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class SecuredMicroServiceInterceptor
		extends
			OAuth2FeignRequestInterceptor {

	@Value("${keycloak.token-uri}")
	private String accessTokenUri;

	@Value("${spring.security.oauth2.client.registration.secured.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.secured.client-secret}")
	private String clientSecret;

	private final OAuth2ClientContext oAuth2ClientContext;

	@Value("${keycloak.authorization-uri}")
	private String userAuthorizationUri;

	private final OAuth2ProtectedResourceDetails resource;

	public SecuredMicroServiceInterceptor(
			OAuth2ClientContext oAuth2ClientContext,
			OAuth2ProtectedResourceDetails resource) {
		super(oAuth2ClientContext, resource);
		this.oAuth2ClientContext = oAuth2ClientContext;
		this.resource = resource;
		acquireAccessToken(resource);
	}

	private AccessTokenProvider accessTokenProvider() {
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		List<AccessTokenProvider> accessTokenProviders = Arrays.asList(
				new AuthorizationCodeAccessTokenProvider(),
				new ImplicitAccessTokenProvider(),
				new ResourceOwnerPasswordAccessTokenProvider(),
				new ClientCredentialsAccessTokenProvider());

		AccessTokenProviderChain result = new AccessTokenProviderChain(
				accessTokenProviders);
		result.setRequestFactory(requestFactory);

		return result;
	}

	@Override
	protected OAuth2AccessToken acquireAccessToken() {
		return acquireAccessToken(resource);
	}

	/**
	 * @see OAuth2FeignRequestInterceptor#acquireAccessToken()
	 */
	protected OAuth2AccessToken acquireAccessToken(
			OAuth2ProtectedResourceDetails resource)
			throws UserRedirectRequiredException {
		AccessTokenRequest tokenRequest = oAuth2ClientContext
				.getAccessTokenRequest();
		if (tokenRequest == null) {
			throw new AccessTokenRequiredException(
					"Cannot find valid context on request for resource '"
							+ resource.getId() + "'.",
					resource);
		}
		String stateKey = tokenRequest.getStateKey();
		if (stateKey != null) {
			tokenRequest.setPreservedState(
					oAuth2ClientContext.removePreservedState(stateKey));
		}
		OAuth2AccessToken existingToken = oAuth2ClientContext.getAccessToken();
		if (existingToken != null) {
			oAuth2ClientContext.setAccessToken(existingToken);
		}
		OAuth2AccessToken obtainableAccessToken;
		obtainableAccessToken = accessTokenProvider()
				.obtainAccessToken(resource, tokenRequest);
		if (obtainableAccessToken == null
				|| obtainableAccessToken.getValue() == null) {
			throw new IllegalStateException(
					" Access token provider returned a null token, which is illegal according to the contract.");
		}
		oAuth2ClientContext.setAccessToken(obtainableAccessToken);
		return obtainableAccessToken;
	}
}