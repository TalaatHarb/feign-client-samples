package net.talaatharb.samples.securedcrud.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.talaatharb.samples.securedcrud.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
}
