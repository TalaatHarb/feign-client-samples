package net.talaatharb.samples.unsecuredcrud.api;

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
import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;
import net.talaatharb.samples.unsecuredcrud.service.ItemService;

@CrossOrigin
@RestController
@RequestMapping("/api/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * Create an Item
	 * 
	 * @param dto
	 *            The item to create
	 * @return The item after creation including its id and its URI in the
	 *         headers
	 */
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create an Item")
	public ResponseEntity<ItemDto> create(
			@ApiParam(value = "Item object", type = "ItemDto") @RequestBody() ItemDto dto) {
		dto = itemService.create(dto);

		// Create resource location
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(dto.getId()).toUri();

		// Send location in response
		return ResponseEntity.created(location).body(dto);
	}

	/**
	 * Delete an Item by id
	 * 
	 * @param id
	 *            The id of the item
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete an Item by id")
	public void delete(
			@ApiParam(value = "Item Id", type = "UUID", allowMultiple = false) @PathVariable("id") UUID id) {
		itemService.delete(id);
	}

	/**
	 * Fetch an Item by id
	 * 
	 * @param id
	 *            The id of the item
	 * @return The item after fetching
	 */
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List all Items")
	public List<ItemDto> readAll() {
		return itemService.readAll();
	}

	/**
	 * Fetch an Item by id
	 * 
	 * @param id
	 *            The id of the item
	 * @return The item after fetching
	 */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Fetch an Item by id")
	public ItemDto readOne(
			@ApiParam(value = "Item Id", type = "UUID", allowMultiple = false) @PathVariable("id") UUID id) {
		return itemService.readOne(id);
	}

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
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Update an Item")
	public ResponseEntity<URI> update(
			@ApiParam(value = "Item id", type = "UUID") @PathVariable("id") final UUID id,
			@ApiParam(value = "Item object", type = "ItemDto") @RequestBody() ItemDto dto) {
		itemService.update(id, dto);
		// Create resource location
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.build().toUri();

		// Send location in response
		return ResponseEntity.noContent().location(location).build();
	}

}
