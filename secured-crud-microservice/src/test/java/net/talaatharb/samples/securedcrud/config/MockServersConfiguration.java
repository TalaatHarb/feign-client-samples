package net.talaatharb.samples.securedcrud.config;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@ActiveProfiles("test")
@TestConfiguration
@SpringBootConfiguration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
public class MockServersConfiguration {

	public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
	public static final String TOKEN_PATH = "/auth/realms/sample-realm/protocol/openid-connect/token";

	@Bean(value = "mockSecuredServer", destroyMethod = "stop")
	WireMockServer mockSecuredServer(
			@Value("${secured.port}") final String securedServicePort) {
		WireMockServer mockSecuredServer = new WireMockServer(
				wireMockConfig().port(Integer.parseInt(securedServicePort)));

		mockSecuredServer.start();

		return mockSecuredServer;
	}

	@Bean(value = "oAuth2Mock", destroyMethod = "stop")
	WireMockServer oAuth2Mock(
			@Value("${keycloak.port}") final String keyCloakPort) {
		// Mock OAuth2 provider
		final WireMockServer mockOAuth2Provider = new WireMockServer(
				wireMockConfig().port(Integer.parseInt(keyCloakPort)));

		mockOAuth2Provider.start();

		mockOAuth2Provider
				.stubFor(WireMock.post(urlPathEqualTo(TOKEN_PATH)).willReturn(
						okJson("{\"token_type\": \"Bearer\",\"access_token\":\""
								+ TOKEN + "\"}")));

		return mockOAuth2Provider;
	}

	@Bean
	ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}
}
