package net.talaatharb.samples.unsecuredcrud.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.talaatharb.samples.unsecuredcrud.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
}
