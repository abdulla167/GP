package com.server.mothercare.mothercare.repositories;

import com.server.mothercare.mothercare.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo {
    public UserDetails getUserByUsername(String username);
    public boolean registerUser(User user);
}
