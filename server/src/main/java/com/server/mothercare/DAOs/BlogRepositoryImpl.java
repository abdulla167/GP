package com.server.mothercare.DAOs;

import com.server.mothercare.entities.post.Blog;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class BlogRepositoryImpl implements BlogRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    private Timestamp last_date;
    private  int last_id ;


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



}
