package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Comment;
import com.server.mothercare.entities.Like;
import com.server.mothercare.entities.Post;
import com.server.mothercare.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO{
    @Autowired
    private EntityManager entityManager;

    private Timestamp last_date;
    private  int last_id ;

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
    public List<Post> getPosts(int cycle) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        List<Post> posts = null;
        Query theQuery;
        if (cycle == 1){
            theQuery = currentSession.createQuery("from Post   order by id DESC ").setMaxResults(2);
        }else{
            theQuery = currentSession.createQuery("from Post where id<:last_id ORDER BY id DESC ").setParameter("last_id",last_id).setMaxResults(2);
        }


        try {
            posts = theQuery.getResultList();
//            last_date = posts.get(posts.toArray().length - 1 ).getDate();
            if (!posts.isEmpty()) {
                last_id = posts.get(posts.toArray().length - 1).getId();
                System.out.println("here " + last_id + "here");
            }

            return posts;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Post getPostById(int theId) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery("from Post where id=:theId");

        Post post = null;
        try {
            post = (Post) theQuery.setParameter("theId", theId).getResultList().get(0);
            return post;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addLike(Like theLike) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        try {
            currentSession.saveOrUpdate(theLike);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Post> likedPosts(User theUser) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        List<Post> posts = new ArrayList<Post>();
        Query theQuery = currentSession.createQuery("from Likes where user.username=:userName");
        System.out.println(theUser.getUsername());
        try{
            List<Like> likes = (List<Like>) theQuery.setParameter("userName",theUser.getUsername()).getResultList();
            for( Like like: likes ){
                posts.add(like.getPost());
            }
            return posts;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
