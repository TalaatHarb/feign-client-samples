package net.talaatharb.samples.securedcrud.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.talaatharb.samples.securedcrud.dto.UserDto;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	private static final int COUNT = 10;

	/**
	 * Get all the users
	 * 
	 * @return List of users
	 */
	@GetMapping(path = "")
	public List<UserDto> findAll() {
		log.info("Find all called");

		final List<UserDto> result = new ArrayList<>();

		for (int i = 0; i < COUNT; i++) {
			result.add(new UserDto());
		}

		return result;
	}

}
