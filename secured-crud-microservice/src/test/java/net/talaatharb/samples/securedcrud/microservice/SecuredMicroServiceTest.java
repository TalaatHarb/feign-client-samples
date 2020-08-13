package net.talaatharb.samples.securedcrud.microservice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.talaatharb.samples.securedcrud.config.MockServersConfiguration;
import net.talaatharb.samples.securedcrud.config.SecuredMicroServiceConfiguration;
import net.talaatharb.samples.securedcrud.config.WebSecurityConfiguration;
import net.talaatharb.samples.securedcrud.dto.UserDto;

@EnableFeignClients(value = "net.talaatharb")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {
		MockServersConfiguration.class, WebSecurityConfiguration.class,
		SecuredMicroServiceConfiguration.class, FeignAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class})
@ActiveProfiles("test")
class SecuredMicroServiceTest {

	@Autowired
	private SecuredMicroService securedMicroService;

	@Test
	void testCreate() {
		ResponseEntity<UserDto> response = securedMicroService
				.create(new UserDto());

		assertTrue(HttpStatus.CREATED.equals(response.getStatusCode()));
	}

	@Test
	void testDelete() {
		fail("Not implemented yet");
	}

	@Test
	void testReadAll() {
		fail("Not implemented yet");
	}

	@Test
	void testReadOne() {
		fail("Not implemented yet");

	}

	@Test
	void testUpdate() {
		fail("Not implemented yet");
	}

}
