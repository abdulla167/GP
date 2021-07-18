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

    @Override
    public List<Blog> getBlogs(int currentId, String userName, String category) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        List<Blog> blogs = null;
        System.out.println("here " );
        Query theQuery;
        if (currentId == 0){
            if ( userName.equals("mother-care")){
                if(category.equals("all")) {
                    theQuery = currentSession.createQuery("from Blog where user.username=:userName ORDER BY id DESC ")
                            .setParameter("userName",userName);
                } else {
                    theQuery = currentSession.createQuery("from Blog where (user.username=:userName and categories LIKE:category )ORDER BY id DESC ")
                            .setParameter("userName",userName).setParameter("category", "%"+ category+"%");
                }
            } else {
                if (category.equals("all")) {
                    theQuery = currentSession.createQuery("from Blog ORDER BY id DESC ");
                } else {
                    theQuery = currentSession.createQuery("from Blog where categories LIKE:category ORDER BY id DESC")
                            .setParameter("category", "%"+ category+"%");
                }
            }

        } else {
            if ( userName.equals("mother-care")){
                 if (category.equals("all")) {
                     theQuery = currentSession.createQuery("from Blog where (id<:currentId and user.username=:userName) ORDER BY id DESC ")
                             .setParameter("currentId",currentId).setParameter("userName",userName);
                 } else {
                     theQuery = currentSession.createQuery("from Blog where (id<:currentId and user.username=:userName and categories LIKE:category)" +
                             " ORDER BY id DESC ").setParameter("currentId", currentId).setParameter("category", "%"+ category+"%")
                             .setParameter("userName", userName);
                 }

            } else {
                if (category.equals("all")) {
                    theQuery = currentSession.createQuery("from Blog where id<:currentId ORDER BY id DESC ")
                            .setParameter("currentId", currentId);
                } else {
                    theQuery = currentSession.createQuery("from Blog where (id<:currentId and categories LIKE:category)ORDER BY id DESC ")
                            .setParameter("currentId", currentId).setParameter("category", "%" + category + "%");
                }
            }
        }

        try {
            blogs = theQuery.setMaxResults(12).getResultList();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return blogs;
    }

    @Override
    public List<Blog> getUserBlogs(String userName) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        List<Blog> blogs = null;
        Query theQuery = currentSession.createQuery("from Blog  where user.username=:userName");
        try{
            blogs = theQuery.setParameter("userName", userName).getResultList();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return blogs;
    }

    @Override
    public long blogsCount(String userNmae, String category) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        long count = 0;
        Query theQuery;
        if (userNmae.equals("mother-care")) {
            if (category.equals("all")) {
                theQuery = currentSession.createQuery("select count(*) from Blog where user.username =: userNmae")
                        .setParameter("userNmae", userNmae);
            } else {
                theQuery = currentSession.createQuery("select count(*) from Blog where user.username =: userNmae and categories LIKE:category").
                        setParameter("userNmae", userNmae).setParameter("category", "%" + category + "%");
            }
        } else {
            if (category.equals("all")) {
                theQuery = currentSession.createQuery("select count(*) from Blog");
            } else {
                theQuery = currentSession.createQuery("select count(*) from Blog where categories LIKE:category")
                        .setParameter("category", "%" + category + "%");
            }
        }

        try {
            count = (long)theQuery.getSingleResult();
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
        return count;
    }

}
