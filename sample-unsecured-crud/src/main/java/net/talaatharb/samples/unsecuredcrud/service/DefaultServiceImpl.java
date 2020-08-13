package net.talaatharb.samples.unsecuredcrud.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import net.talaatharb.samples.unsecuredcrud.mapper.EntityMapper;
import net.talaatharb.samples.unsecuredcrud.model.BaseEntity;

@RequiredArgsConstructor
public class DefaultServiceImpl<D, I extends Serializable, E extends BaseEntity<I>>
		implements
			DefaultService<D, I> {

	protected final EntityMapper<D, E> mapper;

	protected final JpaRepository<E, I> repo;

	@Override
	@Transactional
	public D create(D dto) {
		final E entity = repo.save(mapper.toEntity(dto));
		return mapper.toDto(entity);
	}

	@Override
	@Transactional
	public void delete(I id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public List<D> readAll() {
		return mapper.toDto(repo.findAll());
	}

	@Override
	@Transactional
	public D readOne(I id) {
		return mapper.toDto(repo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Unable to find entity with id = " + id.toString())));
	}

	@Override
	@Transactional
	public void update(I id, D dto) {
		if (repo.findById(id).isPresent()) {
			final E entity = mapper.toEntity(dto);
			entity.setId(id);
			repo.save(entity);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Unable to find entity with id = " + id.toString());
		}
	}

}
