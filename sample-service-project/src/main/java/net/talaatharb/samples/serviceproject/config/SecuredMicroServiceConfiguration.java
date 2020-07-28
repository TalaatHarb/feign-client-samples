package net.talaatharb.samples.serviceproject.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;

@EnableOAuth2Sso
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

	@Value("${keycloak.authorization-uri}")
	private String userAuthorizationUri;

	 @Bean
	public RequestInterceptor oAuth2RequestInterceptor() {
		return new SecuredMicroServiceInterceptor(oAuth2ClientContext,
				resource());
	}

	@Bean
	public RestTemplate oAuthRestTemplate(
			OAuth2ProtectedResourceDetails resource) {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource,
				oAuth2ClientContext);

		return restTemplate;
	}

	@Bean
	public OAuth2ProtectedResourceDetails resource() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		// resource.setUserAuthorizationUri(userAuthorizationUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType(
				AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		resource.setScope(Arrays.asList("roles"));
		return resource;
	}
}
