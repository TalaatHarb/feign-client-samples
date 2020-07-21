package net.talaatharb.samples.unsecuredcrud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;

@CrossOrigin
@RestController
@RequestMapping("/api/items")
public class ItemController {

	private static final int COUNT = 10;

	/**
	 * Get all the items
	 * @return
	 */
	@GetMapping(path = "")
	public List<ItemDto> findAll() {
		final List<ItemDto> result = new ArrayList<>();

		for (int i = 0; i < COUNT; i++) {
			result.add(new ItemDto());
		}

		return result;
	}

}
