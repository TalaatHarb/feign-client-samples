package net.talaatharb.samples.unsecuredcrud.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Item extends BaseEntity<UUID> {
	private static final long serialVersionUID = 8614385295494761828L;

	private Date createdAt = new Date();

	private String name;
}
