package com.server.mothercare.services;

import com.server.mothercare.DAOs.UserDAO;
import com.server.mothercare.DAOs.UserDAOImpl;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public boolean registerUser(User theUser) {
        return this.userDAO.registerUser(theUser);
    }

    @Override
    public UserProfile getUserProfile(int profileOwnerId) {
        return null;
    }


}
