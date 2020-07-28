package net.talaatharb.samples.serviceproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.RequestInterceptor;

public class SecuredMicroServiceConfiguration {

	@Value("${keycloak.token-uri}")
	private String accessTokenUri;

	@Value("${spring.security.oauth2.client.registration.secured.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.secured.client-secret}")
	private String clientSecret;

	private final OAuth2ClientContext oAuth2ClientContext;

	public SecuredMicroServiceConfiguration() {
		this.oAuth2ClientContext = new DefaultOAuth2ClientContext();
	}

	@Bean
	public RequestInterceptor oAuth2RequestInterceptor(
			OAuth2ProtectedResourceDetails resourceDetails) {
		return new SecuredMicroServiceInterceptor(oAuth2ClientContext,
				resourceDetails);
	}

	@Bean
	public OAuth2ProtectedResourceDetails resourceDetails() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setAccessTokenUri(accessTokenUri);
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(clientSecret);
		return resourceDetails;
	}
}
