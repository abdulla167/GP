package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Post;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO{
    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean save(Post thePost) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        try {
            currentSession.saveOrUpdate(thePost);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public List<Post> getPosts() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("from Post ");
        List<Post> posts = null;
        try {
            posts = theQuery.getResultList();
            return posts;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
