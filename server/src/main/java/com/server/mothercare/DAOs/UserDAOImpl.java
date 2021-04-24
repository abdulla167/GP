package com.server.mothercare.DAOs;

import com.server.mothercare.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAOCustom{

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
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
