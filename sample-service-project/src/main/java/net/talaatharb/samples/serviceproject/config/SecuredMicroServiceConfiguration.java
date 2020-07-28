package net.talaatharb.samples.serviceproject.config;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Provider;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.RequestInterceptor;

public class SecuredMicroServiceConfiguration {

	private static final String SECURED_CLIENT_NAME = "secured";

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
	public OAuth2ProtectedResourceDetails resourceDetails(
			OAuth2ClientProperties details) {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		
		final Registration client = details.getRegistration()
				.get(SECURED_CLIENT_NAME);
		final Provider provider = details.getProvider()
				.get(client.getProvider());
		
		resourceDetails.setAccessTokenUri(provider.getTokenUri());
		resourceDetails.setClientId(client.getClientId());
		resourceDetails.setClientSecret(client.getClientSecret());
		resourceDetails.setScope(new ArrayList<>(client.getScope()));
		
		return resourceDetails;
	}
}
