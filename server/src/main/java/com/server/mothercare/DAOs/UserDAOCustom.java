package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;

import java.util.Optional;

public interface UserDAOCustom {
    public User userbyUserName(String theUserName);
    public Optional<User> getUserbyUserName(String theUserName);

}
