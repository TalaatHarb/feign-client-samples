package net.talaatharb.samples.securedcrud.config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.util.UUID;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import lombok.Getter;
import net.talaatharb.samples.securedcrud.dto.UserDto;

@ActiveProfiles("test")
@TestConfiguration
@SpringBootConfiguration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
public class MockServersConfiguration {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String SERVICE_URL = "/api/users";

	@Value("${keycloak.port}")
	private String keyCloakPort;

	@Getter
	private static final UUID ID = UUID.randomUUID();

	@Bean("oAuth2Mock")
	WireMockServer oAuth2Mock(
			@Value("${keycloak.port}") final String keyCloakPort) {
		// Mock OAuth2 provider
		final WireMockServer mockOAuth2Provider = new WireMockServer(
				wireMockConfig().port(Integer.parseInt(keyCloakPort)));

		mockOAuth2Provider.start();

		mockOAuth2Provider.stubFor(WireMock.post(urlPathEqualTo(
				"/auth/realms/sample-realm/protocol/openid-connect/token"))
				.willReturn(okJson(
						"{\"token_type\": \"Bearer\",\"access_token\":\"{{randomValue length=20 type='ALPHANUMERIC'}}\"}")));

		return mockOAuth2Provider;
	}

	@Bean("securedServer")
	WireMockServer securedServer(
			@Value("${secured.port}") final String securedServicePort)
			throws JsonProcessingException {
		final WireMockServer mockSecuredServer = new WireMockServer(
				wireMockConfig().port(Integer.parseInt(securedServicePort)));

		mockSecuredServer.start();

		mockSecuredServer.stubFor(WireMock.post(urlPathEqualTo(SERVICE_URL))
				.willReturn(aResponse().withStatus(HttpStatus.CREATED_201)
						.withBody(
								objectMapper.writeValueAsString(new UserDto()))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// read all
		mockSecuredServer.stubFor(WireMock.get(urlPathEqualTo(SERVICE_URL))
				.willReturn(okJson("[]")));

		// read one
		mockSecuredServer.stubFor(WireMock
				.get(urlPathEqualTo(SERVICE_URL + "/" + ID)).willReturn(okJson(
						objectMapper.writeValueAsString(new UserDto(ID)))));

		// update
		mockSecuredServer
				.stubFor(WireMock.put(urlPathEqualTo(SERVICE_URL + "/" + ID)));

		// delete
		mockSecuredServer.stubFor(
				WireMock.delete(urlPathEqualTo(SERVICE_URL + "/" + ID)));

		return mockSecuredServer;
	}

	@Bean
	ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}
}
