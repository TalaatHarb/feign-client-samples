package net.talaatharb.samples.unsecuredcrud.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface for all mapper classes that map a DTO to its Model object/entity
 * and vice versa
 * 
 * @author mharb
 *
 * @param <D>
 *            DTO
 * @param <E>
 *            Entity
 */
public interface EntityMapper<D, E> {

	/**
	 * Map an entity to its corresponding DTO
	 * 
	 * @param entity
	 *            Entity to map
	 * @return The DTO after mapping
	 */
	public abstract D toDto(E entity);

	/**
	 * Map a Collection of entities to its corresponding Collection of DTOs
	 * 
	 * @param entityCollection
	 * @return The Collection of mapped DTOs
	 */
	public default List<D> toDto(final Collection<E> entityCollection) {
		return entityCollection.stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * Map a DTO to its corresponding entity
	 * 
	 * @param dto
	 *            The DTO to map
	 * @return The entity the DTO get mapped to
	 */
	public abstract E toEntity(D dto);

	/**
	 * Map a Collection of DTOs to its corresponding Collection of entities
	 * 
	 * @param dtoCollection
	 *            DTO Collection to map
	 * @return The corresponding entities
	 */
	public default List<E> toEntity(final Collection<D> dtoCollection) {
		return dtoCollection.stream().map(this::toEntity)
				.collect(Collectors.toList());
	}

}
