package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long>, UserDAOCustom {
    User findByUsername(String username);
}
