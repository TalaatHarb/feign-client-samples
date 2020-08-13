package net.talaatharb.samples.securedcrud.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.talaatharb.samples.securedcrud.dto.UserDto;
import net.talaatharb.samples.securedcrud.model.User;

@Configuration
public class MapperBeansConfiguration {
	
	@Bean
	public EntityMapper<UserDto, User> getUserMapper(){
		return new DefaultEntityMapper<>(UserDto.class, User.class);
	}

}
