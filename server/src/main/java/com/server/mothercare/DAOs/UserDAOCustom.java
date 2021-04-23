package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;

public interface UserDAOCustom {
    public User userbyUserName(String theUserName);
}
