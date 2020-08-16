package net.talaatharb.samples.unsecuredcrud.config;

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

@ActiveProfiles("test")
@TestConfiguration
@SpringBootConfiguration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
public class MockServersConfiguration {

	@Bean(value = "mockUnsecuredServer", destroyMethod = "stop")
	WireMockServer mockUnsecuredServer(
			@Value("${unsecured.port}") final String unsecuredServicePort) {
		WireMockServer mockUnsecuredServer = new WireMockServer(
				wireMockConfig().port(Integer.parseInt(unsecuredServicePort)));

		mockUnsecuredServer.start();

		return mockUnsecuredServer;
	}

	@Bean
	ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}
}
