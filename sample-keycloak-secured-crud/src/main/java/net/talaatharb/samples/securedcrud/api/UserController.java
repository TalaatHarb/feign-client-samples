package net.talaatharb.samples.securedcrud.api;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.talaatharb.samples.securedcrud.dto.UserDto;
import net.talaatharb.samples.securedcrud.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Create a user
	 * 
	 * @param dto
	 *            The User to create
	 * @return The User after creation including its id and its URI in the
	 *         headers
	 */
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a user")
	public ResponseEntity<UserDto> create(
			@ApiParam(value = "User object", type = "UserDto") @RequestBody() UserDto dto) {
		dto = userService.create(dto);

		// Create resource location
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(dto.getId()).toUri();

		// Send location in response
		return ResponseEntity.created(location).body(dto);
	}

	/**
	 * Delete a user by id
	 * 
	 * @param id
	 *            The id of the User
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete a user by id")
	public void delete(
			@ApiParam(value = "User Id", type = "UUID", allowMultiple = false) @PathVariable("id") UUID id) {
		userService.delete(id);
	}

	/**
	 * Fetch a user by id
	 * 
	 * @param id
	 *            The id of the User
	 * @return The User after fetching
	 */
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List all Users")
	public List<UserDto> readAll() {
		return userService.readAll();
	}

	/**
	 * Fetch a user by id
	 * 
	 * @param id
	 *            The id of the User
	 * @return The User after fetching
	 */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Fetch a user by id")
	public UserDto readOne(
			@ApiParam(value = "User Id", type = "UUID", allowMultiple = false) @PathVariable("id") UUID id) {
		return userService.readOne(id);
	}

	/**
	 * Update a user given its id and its new form
	 * 
	 * @param id
	 *            The id of the User
	 * @param dto
	 *            The new User form
	 * @return HTTP Response with the User URL in the headers
	 */
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Update a user")
	public ResponseEntity<URI> update(
			@ApiParam(value = "User id", type = "UUID") @PathVariable("id") final UUID id,
			@ApiParam(value = "User object", type = "UserDto") @RequestBody() UserDto dto) {
		userService.update(id, dto);
		// Create resource location
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.build().toUri();

		// Send location in response
		return ResponseEntity.noContent().location(location).build();
	}

}
