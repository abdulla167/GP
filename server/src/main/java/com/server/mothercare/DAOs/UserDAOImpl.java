package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAO{

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public boolean registerUser(User theUser) {
        try {
            Session currentSession = this.entityManager.unwrap(Session.class);
            currentSession.save(theUser);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UserProfile getUserProfile(int profileOwnerId) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return new UserProfile();
    }
}
