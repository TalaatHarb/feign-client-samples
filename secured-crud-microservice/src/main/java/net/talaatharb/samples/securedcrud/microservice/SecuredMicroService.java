package net.talaatharb.samples.securedcrud.microservice;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.talaatharb.samples.securedcrud.config.SecuredMicroServiceConfiguration;
import net.talaatharb.samples.securedcrud.dto.UserDto;

@FeignClient(value = "secured", url = "${secured.url}", configuration = SecuredMicroServiceConfiguration.class)
@RequestMapping("/api/users")
public interface SecuredMicroService {

	/**
	 * Create a user
	 * 
	 * @param dto
	 *            The User to create
	 * @return The User after creation including its id and its URI in the
	 *         headers
	 */
	@PostMapping("")
	public abstract ResponseEntity<UserDto> create(@RequestBody() UserDto dto);

	/**
	 * Delete a user by id
	 * 
	 * @param id
	 *            The id of the User
	 */
	@DeleteMapping("/{id}")
	public abstract void delete(@PathVariable("id") UUID id);

	/**
	 * Fetch a user by id
	 * 
	 * @param id
	 *            The id of the User
	 * @return The User after fetching
	 */
	@GetMapping("")
	public abstract List<UserDto> readAll();

	/**
	 * Fetch a user by id
	 * 
	 * @param id
	 *            The id of the User
	 * @return The User after fetching
	 */
	@GetMapping("/{id}")
	public abstract UserDto readOne(@PathVariable("id") UUID id);

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
	public abstract ResponseEntity<URI> update(
			@PathVariable("id") final UUID id, @RequestBody() UserDto dto);
}
