package net.talaatharb.samples.securedcrud.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import net.talaatharb.samples.securedcrud.dto.UserDto;
import net.talaatharb.samples.securedcrud.mapper.EntityMapper;
import net.talaatharb.samples.securedcrud.model.User;

@Service
public class UserServiceImpl extends DefaultServiceImpl<UserDto, UUID, User>
		implements
			UserService {

	public UserServiceImpl(final EntityMapper<UserDto, User> mapper,
			final JpaRepository<User, UUID> repo) {
		super(mapper, repo);
	}

}
