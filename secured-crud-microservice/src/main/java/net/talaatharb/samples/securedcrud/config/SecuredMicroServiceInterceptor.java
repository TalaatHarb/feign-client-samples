package net.talaatharb.samples.securedcrud.config;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

public class SecuredMicroServiceInterceptor
		extends
			OAuth2FeignRequestInterceptor {

	public SecuredMicroServiceInterceptor(
			OAuth2ClientContext oAuth2ClientContext,
			OAuth2ProtectedResourceDetails resourceDetails) {
		super(oAuth2ClientContext, resourceDetails);
		acquireAccessToken();
	}
}