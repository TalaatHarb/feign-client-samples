package net.talaatharb.samples.unsecuredcrud.microservice;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import net.talaatharb.samples.unsecuredcrud.config.MockServersConfiguration;
import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;
import net.talaatharb.samples.unsecuredcurd.microservice.UnsecuredMicroService;

@EnableFeignClients(value = "net.talaatharb")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {
		MockServersConfiguration.class, FeignAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class})
@ActiveProfiles("test")
class UnsecuredMicroServiceTest {

	@Value("${unsecured.apiUrl}")
	private String apiUrl;

	@Autowired
	@Qualifier("mockUnsecuredServer")
	private WireMockServer mockUnsecuredServer;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private UnsecuredMicroService unsecuredMicroService;

	@Test
	void testCreate() throws JsonProcessingException {
		// Arrange for create
		final ItemDto expectedResult = new ItemDto();

		mockUnsecuredServer.stubFor(WireMock.post(urlPathEqualTo(apiUrl))
				.willReturn(aResponse().withStatus(HttpStatus.CREATED.value())
						.withBody(
								objectMapper.writeValueAsString(expectedResult))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// Action: create
		final ResponseEntity<ItemDto> response = unsecuredMicroService
				.create(new ItemDto());

		// Assert

		// Action took effect
		mockUnsecuredServer
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

		mockUnsecuredServer.stubFor(
				WireMock.delete(urlPathEqualTo(apiUrlWithId)).willReturn(
						aResponse().withStatus(HttpStatus.NO_CONTENT.value())));

		// Action: delete
		unsecuredMicroService.delete(id);

		// Assert action took effect
		mockUnsecuredServer.verify(
				WireMock.deleteRequestedFor(urlPathEqualTo(apiUrlWithId)));

	}

	@Test
	void testReadAll() throws JsonProcessingException {
		// Arrange for read all
		final List<ItemDto> expectedResult = Arrays.asList(new ItemDto(),
				new ItemDto());

		mockUnsecuredServer.stubFor(WireMock.get(urlPathEqualTo(apiUrl))
				.willReturn(aResponse().withStatus(HttpStatus.OK.value())
						.withBody(
								objectMapper.writeValueAsString(expectedResult))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// Action: read all
		final List<ItemDto> response = unsecuredMicroService.readAll();

		// Assert

		// Action took effect
		mockUnsecuredServer
				.verify(WireMock.getRequestedFor(urlPathEqualTo(apiUrl)));

		// With expected Data
		assertEquals(expectedResult, response);
	}

	@Test
	void testReadOne() throws JsonProcessingException {
		// Arrange for read one
		final UUID id = UUID.randomUUID();
		final String apiUrlWithId = apiUrl + "/" + id;

		final ItemDto expectedResult = new ItemDto(id);

		mockUnsecuredServer.stubFor(WireMock.get(urlPathEqualTo(apiUrlWithId))
				.willReturn(aResponse().withStatus(HttpStatus.OK.value())
						.withBody(
								objectMapper.writeValueAsString(expectedResult))
						.withHeader("Content-type",
								MediaType.APPLICATION_JSON.toString())));

		// Action: read one
		final ItemDto response = unsecuredMicroService.readOne(id);

		// Assert

		// Action took effect
		mockUnsecuredServer
				.verify(WireMock.getRequestedFor(urlPathEqualTo(apiUrlWithId)));

		// With expected Data
		assertEquals(expectedResult, response);
	}

	@Test
	void testUpdate() throws JsonProcessingException {
		// Arrange for update
		final UUID id = UUID.randomUUID();
		final String apiUrlWithId = apiUrl + "/" + id;

		mockUnsecuredServer.stubFor(WireMock.put(urlPathEqualTo(apiUrlWithId))
				.willReturn(aResponse()
						.withStatus(HttpStatus.NO_CONTENT.value())
						.withHeader("Location",
								mockUnsecuredServer.baseUrl() + apiUrlWithId)));

		// Action: update
		final ResponseEntity<URI> response = unsecuredMicroService.update(id,
				new ItemDto(id));

		// Assert

		// Action took effect
		mockUnsecuredServer
				.verify(WireMock.putRequestedFor(urlPathEqualTo(apiUrlWithId)));

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
