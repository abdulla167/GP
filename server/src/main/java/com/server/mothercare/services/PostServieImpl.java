package com.server.mothercare.services;

import com.server.mothercare.DAOs.PostDAO;
import com.server.mothercare.entities.Comment;
import com.server.mothercare.entities.Like;
import com.server.mothercare.entities.Post;
import com.server.mothercare.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostServieImpl implements PostService{

    @Autowired
    private PostDAO postDAO;

    @Override
    @Transactional
    public boolean save(Post thePost) {
        thePost.setId(0);
        return postDAO.save(thePost);
    }

    @Override
    @Transactional
    public boolean saveComment(Comment theComment) {
        theComment.setId(0);
        return postDAO.saveComment(theComment);
    }

    @Override
    @Transactional
    public boolean update(Post thePost) {
        return postDAO.save(thePost);
    }

    @Override
    @Transactional
    public boolean updateComment(Comment theComment) {
        return postDAO.saveComment(theComment);
    }

    @Override
    @Transactional
    public List<Post> getPosts(int cycle) {
        return postDAO.getPosts( cycle);
    }

    @Override
    @Transactional
    public Post getPostById(int theId) {
        return postDAO.getPostById(theId);
    }

    @Override
    public boolean addLike(Like theLike) {
        return postDAO.addLike(theLike);
    }

    @Override
    public List<Post> likedPosts(User theUser) {
        return postDAO.likedPosts(theUser);
    }
}
