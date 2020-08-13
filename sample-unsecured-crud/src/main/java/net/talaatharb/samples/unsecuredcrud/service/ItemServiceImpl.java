package net.talaatharb.samples.unsecuredcrud.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import net.talaatharb.samples.unsecuredcrud.dto.ItemDto;
import net.talaatharb.samples.unsecuredcrud.mapper.EntityMapper;
import net.talaatharb.samples.unsecuredcrud.model.Item;

@Service
public class ItemServiceImpl extends DefaultServiceImpl<ItemDto, UUID, Item>
		implements
			ItemService {

	public ItemServiceImpl(final EntityMapper<ItemDto, Item> mapper,
			final JpaRepository<Item, UUID> repo) {
		super(mapper, repo);
	}

}
