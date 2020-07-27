package net.talaatharb.samples.unsecuredcrud.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;

@CrossOrigin
@RestController
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

	private static final int COUNT = 10;

	/**
	 * Get all the items
	 * @return List of items
	 */
	@GetMapping(path = "")
	public List<ItemDto> findAll() {
		log.info("Find all called");
		
		final List<ItemDto> result = new ArrayList<>();

		for (int i = 0; i < COUNT; i++) {
			result.add(new ItemDto());
		}

		return result;
	}

}
