package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long>, UserDAOCustom {
    Optional<User> findByUsername(String username);
}
