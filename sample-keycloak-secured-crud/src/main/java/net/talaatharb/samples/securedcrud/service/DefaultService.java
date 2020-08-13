package net.talaatharb.samples.securedcrud.service;

import java.io.Serializable;
import java.util.List;

public interface DefaultService<D, I extends Serializable> {
	public abstract D create(D dto);

	public abstract void delete(I id);

	public abstract List<D> readAll();

	public abstract D readOne(I id);

	public abstract void update(I id, D dto);
}
