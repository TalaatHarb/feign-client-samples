package net.talaatharb.samples.unsecuredcurd.microservice;

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

import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;

@FeignClient(value = "unsecured", url = "${unsecured.url}")
@RequestMapping("${unsecured.apiUrl}")
public interface UnsecuredMicroService {
	
	/**
	 * Create an Item
	 * 
	 * @param dto
	 *            The item to create
	 * @return The item after creation including its id and its URI in the
	 *         headers
	 */
	@PostMapping("")
	public ResponseEntity<ItemDto> create(@RequestBody() ItemDto dto);

	/**
	 * Delete an Item by id
	 * 
	 * @param id
	 *            The id of the item
	 */
	@DeleteMapping("/{id}")
	public abstract void delete(@PathVariable("id") UUID id);

	/**
	 * List all Items
	 * 
	 * @return A list of all the items
	 */
	@GetMapping("")
	public abstract List<ItemDto> readAll();

	/**
	 * Fetch an Item by id
	 * 
	 * @param id
	 *            The id of the item
	 * @return The item after fetching
	 */
	@GetMapping("/{id}")
	public abstract ItemDto readOne(@PathVariable("id") UUID id);

	/**
	 * Update an Item given its id and its new form
	 * 
	 * @param id
	 *            The id of the item
	 * @param dto
	 *            The new item form
	 * @return HTTP Response with the item URL in the headers
	 */
	@PutMapping("/{id}")
	public abstract ResponseEntity<URI> update(
			@PathVariable("id") final UUID id, @RequestBody() ItemDto dto);
}
