package net.talaatharb.samples.unsecuredcrud.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

	private Date createdAt = new Date();

	private UUID id = UUID.randomUUID();

	private String name = UUID.randomUUID().toString();

	public ItemDto(final UUID id) {
		this.id = id;
	}

}
