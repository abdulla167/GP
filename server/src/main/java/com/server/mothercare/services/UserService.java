package com.server.mothercare.services;

import com.server.mothercare.entities.Like;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;

public interface UserService {
    public boolean registerUser(User theUser);
    public User userbyUserName(String theUserName);
    public UserProfile getUserProfile(int profileOwnerId);
    public boolean update(User thUser);
}

