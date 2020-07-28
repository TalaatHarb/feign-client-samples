package net.talaatharb.samples.unsecuredcurd.microservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;

@FeignClient(value = "unsecured", url = "${unsecured.url}")
@RequestMapping("/api/items")
public interface UnsecuredMicroService {
	/**
	 * Get all the items
	 * @return List of items
	 */
	@GetMapping(path = "")
	public List<ItemDto> findAll();
}
