package net.talaatharb.samples.securedcrud.model;

import java.util.UUID;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class User extends BaseEntity<UUID> {

	private static final long serialVersionUID = 8288067382064639243L;

	private String firstName;

	private String lastName;
}
