package net.talaatharb.samples.serviceproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.talaatharb.samples.serviceproject.microservice.SecuredMicroService;
import net.talaatharb.samples.serviceproject.microservice.UnsecuredMicroService;
import net.talaatharb.samples.serviceproject.microservice.dto.ItemDto;
import net.talaatharb.samples.serviceproject.microservice.dto.UserDto;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class CrudController {

	private static final String UNSECURED_SERVICE_NAME = "unsecured";

	private static final String SECURED_SERVICE_NAME = "secured";

	private static final String FIND_ALL_MESSAGE = "Find all from {} called";

	@Autowired
	private UnsecuredMicroService unsecuredService;

	@Autowired
	private SecuredMicroService securedService;

	@GetMapping(path = "/unsecured")
	public List<ItemDto> findAllFromUnsecured() {
		log.info(FIND_ALL_MESSAGE, UNSECURED_SERVICE_NAME);
		return unsecuredService.findAll();
	}

	@GetMapping(path = "/secured")
	public List<UserDto> findAllFromSecured() {
		log.info(FIND_ALL_MESSAGE, SECURED_SERVICE_NAME);
		return securedService.findAll();
	}
}
