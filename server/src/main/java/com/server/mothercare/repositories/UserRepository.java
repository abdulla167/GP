package com.server.mothercare.repositories;

import com.server.mothercare.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
