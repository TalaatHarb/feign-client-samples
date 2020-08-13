package net.talaatharb.samples.unsecuredcrud.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;
import net.talaatharb.samples.unsecuredcrud.model.Item;

@Configuration
public class MapperBeansConfiguration {
	
	@Bean
	public EntityMapper<ItemDto, Item> getItemMapper(){
		return new DefaultEntityMapper<>(ItemDto.class, Item.class);
	}

}
