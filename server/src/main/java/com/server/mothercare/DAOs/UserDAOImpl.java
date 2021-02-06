package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User userbyUserName(String theUserName) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery(" from User where username=:theUserName");
        User user = null;
        try {
            user = (User) query.setParameter("theUserName", theUserName).getResultList().get(0);
            System.out.println(user);
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public UserProfile getUserProfile(int profileOwnerId) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return new UserProfile();
    }

    @Override
    public boolean save(User theUser) {
        try {
            Session currentSession = this.entityManager.unwrap(Session.class);
            currentSession.saveOrUpdate(theUser);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
