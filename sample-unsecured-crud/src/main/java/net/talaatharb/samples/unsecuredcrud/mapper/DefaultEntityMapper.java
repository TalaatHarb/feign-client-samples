package net.talaatharb.samples.unsecuredcrud.mapper;

import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultEntityMapper<D, E> implements EntityMapper<D, E> {

	private static final ModelMapper MODEL_MAPPER = new ModelMapper();

	private final Class<D> typeD;

	private final Class<E> typeE;

	@Override
	public D toDto(E entity) {
		return MODEL_MAPPER.map(entity, typeD);
	}

	@Override
	public E toEntity(D dto) {
		return MODEL_MAPPER.map(dto, typeE);
	}

}
