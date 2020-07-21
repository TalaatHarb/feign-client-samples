package net.talaatharb.samples.serviceproject.microservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "unsecured", url = "${unsecured.host}")
@RequestMapping("/api/items")
public interface UnsecuredMicroService {
	/**
	 * Get all the items
	 * @return List of items
	 */
	@GetMapping(path = "")
	public List<ItemDto> findAll();
}
