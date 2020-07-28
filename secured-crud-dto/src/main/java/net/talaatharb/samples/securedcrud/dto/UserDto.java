package net.talaatharb.samples.securedcrud.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDto {

	private UUID id = UUID.randomUUID();

	private String firstName = UUID.randomUUID().toString();

	private String lastName = UUID.randomUUID().toString();

}
