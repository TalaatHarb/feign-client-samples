package net.talaatharb.samples.securedcrud.microservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.talaatharb.samples.securedcrud.config.SecuredMicroServiceConfiguration;
import net.talaatharb.samples.securedcrud.dto.UserDto;

@FeignClient(value = "secured", url = "${secured.url}", configuration = SecuredMicroServiceConfiguration.class)
@RequestMapping("/api/users")
public interface SecuredMicroService {

	@GetMapping(path = "")
	public abstract List<UserDto> findAll();
}
