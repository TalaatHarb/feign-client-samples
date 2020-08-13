package net.talaatharb.samples.unsecuredcrud.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -5681664418917702110L;

	@Id
	private T id;

}
