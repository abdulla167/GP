package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;

public interface UserDAO {
    public boolean registerUser(User theUser);

    public UserProfile getUserProfile(int profileOwnerId);
}
