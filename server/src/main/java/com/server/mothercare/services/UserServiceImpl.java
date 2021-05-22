package com.server.mothercare.services;

import com.server.mothercare.DAOs.UserDAO;
import com.server.mothercare.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User registerUser(User theUser) {
        theUser.setUserId(0);
        return this.userDAO.save(theUser);
    }

    @Override
    public Optional<User> getUserbyUserName(String theUserName) {
        return userDAO.getUserbyUserName(theUserName);
    }

    @Override
    @Transactional
    public User userbyUserName(String theUserName) {
        return userDAO.userbyUserName(theUserName);
    }

    @Override
    @Transactional
    public User update(User thUser) {
        return userDAO.save(thUser);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userDAO.findById(id);
    }
}
