package net.talaatharb.samples.serviceproject.microservice.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class ItemDto {
	
	private Date createdAt = new Date();
	
	private UUID id = UUID.randomUUID();
	
	private String name = UUID.randomUUID().toString();

}
