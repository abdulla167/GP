package com.server.mothercare.mothercare.repositories;

import com.server.mothercare.mothercare.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl implements UserRepo{
    @Override
    public UserDetails getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean registerUser(User user) {
        return false;
    }
}
