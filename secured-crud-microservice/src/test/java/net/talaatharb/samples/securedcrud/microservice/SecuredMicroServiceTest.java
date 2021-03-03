package net.talaatharb.samples.securedcrud.microservice;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import net.talaatharb.samples.securedcrud.config.MockServersConfiguration;
import net.talaatharb.samples.securedcrud.config.SecuredMicroServiceConfiguration;
import net.talaatharb.samples.securedcrud.config.WebSecurityConfiguration;
import net.talaatharb.samples.securedcrud.dto.UserDto;

@EnableFeignClients(value = "net.talaatharb")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {
		MockServersConfiguration.class, WebSecurityConfiguration.class,
		SecuredMicroServiceConfiguration.class, FeignAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class})
@ActiveProfiles("test")
class SecuredMicroServiceTest {

	@Value("${secured.apiUrl}")
	private String apiUrl;

	@Autowired
	@Qualifier("mockSecuredServer")
	private WireMockServer mockSecuredServer;

	@Autowired
	@Qualifier("oAuth2Mock")
	private WireMockServer oAuth2Mock;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private SecuredMicroService securedMicroService;

	@Test
	void testCreate() throws JsonProcessingException {
		// Arrange for create
		final UserDto expectedResult = new UserDto();

		mockSecuredServer.stubFor(WireMock.post(urlPathEqualTo(apiUrl))
				.willReturn(aResponse().withStatus(HttpStatus.CREATED.value())
						.withBody(
								objectMapper.writeValueAsString(expectedResult))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// Action: create
		final ResponseEntity<UserDto> response = securedMicroService
				.create(new UserDto());

		// Assert

		// Action took effect
		mockSecuredServer
				.verify(WireMock.postRequestedFor(urlPathEqualTo(apiUrl)));

		// With expected Data
		assertEquals(expectedResult, response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	void testDelete() {
		// Arrange setup for delete
		final UUID id = UUID.randomUUID();
		final String apiUrlWithId = apiUrl + "/" + id;

		mockSecuredServer.stubFor(
				WireMock.delete(urlPathEqualTo(apiUrlWithId)).willReturn(
						aResponse().withStatus(HttpStatus.NO_CONTENT.value())));

		// Action: delete
		securedMicroService.delete(id);

		// Assert action took effect
		mockSecuredServer.verify(
				WireMock.deleteRequestedFor(urlPathEqualTo(apiUrlWithId)));

	}

	@Test
	void testReadAll() throws JsonProcessingException {
		// Arrange for read all
		final List<UserDto> expectedResult = Arrays.asList(new UserDto(),
				new UserDto());

		mockSecuredServer.stubFor(WireMock.get(urlPathEqualTo(apiUrl))
				.willReturn(aResponse().withStatus(HttpStatus.OK.value())
						.withBody(
								objectMapper.writeValueAsString(expectedResult))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// Action: read all
		final List<UserDto> response = securedMicroService.readAll();

		// Assert

		// Action took effect
		mockSecuredServer
				.verify(WireMock.getRequestedFor(urlPathEqualTo(apiUrl))
						.withHeader("Authorization", WireMock.equalTo(
								"Bearer " + MockServersConfiguration.TOKEN)));

		// and authentication server called
		oAuth2Mock.verify(WireMock.postRequestedFor(
				urlPathEqualTo(MockServersConfiguration.TOKEN_PATH)));

		// With expected Data
		assertEquals(expectedResult, response);
	}

	@Test
	void testReadOne() throws JsonProcessingException {
		// Arrange for read one
		final UUID id = UUID.randomUUID();
		final String apiUrlWithId = apiUrl + "/" + id;

		final UserDto expectedResult = new UserDto(id);

		mockSecuredServer.stubFor(WireMock.get(urlPathEqualTo(apiUrlWithId))
				.willReturn(aResponse().withStatus(HttpStatus.OK.value())
						.withBody(
								objectMapper.writeValueAsString(expectedResult))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// Action: read one
		final UserDto response = securedMicroService.readOne(id);

		// Assert

		// Action took effect
		mockSecuredServer
				.verify(WireMock.getRequestedFor(urlPathEqualTo(apiUrlWithId)));

		// With expected Data
		assertEquals(expectedResult, response);
	}

	@Test
	void testUpdate() throws JsonProcessingException {
		// Arrange for update
		final UUID id = UUID.randomUUID();
		final String apiUrlWithId = apiUrl + "/" + id;

		mockSecuredServer.stubFor(WireMock.put(urlPathEqualTo(apiUrlWithId))
				.willReturn(aResponse()
						.withStatus(HttpStatus.NO_CONTENT.value())
						.withHeader("Location",
								mockSecuredServer.baseUrl() + apiUrlWithId)));

		// Action: update
		final ResponseEntity<URI> response = securedMicroService.update(id,
				new UserDto(id));

		// Assert

		// Action took effect
		mockSecuredServer
				.verify(WireMock.putRequestedFor(urlPathEqualTo(apiUrlWithId)));

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
