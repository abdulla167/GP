package com.server.mothercare.services;

import com.server.mothercare.entities.User;

public interface UserService {
    public User registerUser(User theUser);
    public User userbyUserName(String theUserName);
    public User update(User thUser);

}

