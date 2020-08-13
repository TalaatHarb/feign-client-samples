package net.talaatharb.samples.securedcrud.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	private UUID id = UUID.randomUUID();

	private String firstName = UUID.randomUUID().toString();

	private String lastName = UUID.randomUUID().toString();

	public UserDto(final UUID id) {
		this.id = id;
	}

}
