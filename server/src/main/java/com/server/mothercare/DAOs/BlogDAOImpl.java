package com.server.mothercare.DAOs;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class BlogDAOImpl implements BlogDAO{
    @Autowired
    private EntityManager entityManager;

    private Timestamp last_date;
    private  int last_id ;

    @Override
    public boolean save(Blog theBlog) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        try {
            currentSession.saveOrUpdate(theBlog);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean saveComment(Comment theComment) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        try {
            currentSession.saveOrUpdate(theComment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Blog> getBlogs(int cycle) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        List<Blog> blogs = null;
        Query theQuery;
        if (cycle == 1){
            theQuery = currentSession.createQuery("from Blog   order by id DESC ").setMaxResults(2);
        }else{
            theQuery = currentSession.createQuery("from Blog where id<:last_id ORDER BY id DESC ").setParameter("last_id",last_id).setMaxResults(2);
        }


        try {
            blogs = theQuery.getResultList();
//            last_date = posts.get(posts.toArray().length - 1 ).getDate();
            if (!blogs.isEmpty()) {
                last_id = blogs.get(blogs.toArray().length - 1).getId();
                System.out.println("here " + last_id + "here");
            }

            return blogs;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Blog getBlogById(int theId) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("from Blog where id=:theId");
        Blog blog = null;
        try {
            blog = (Blog) theQuery.setParameter("theId", theId).getResultList().get(0);
            return blog;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
