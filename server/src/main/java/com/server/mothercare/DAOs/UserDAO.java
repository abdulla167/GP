package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;

public interface UserDAO {
    public User userbyUserName(String theUserName);
    public UserProfile getUserProfile(int profileOwnerId);
    public boolean save(User thUser);
}
