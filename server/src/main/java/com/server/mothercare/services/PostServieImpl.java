package com.server.mothercare.services;

import com.server.mothercare.DAOs.PostDAO;
import com.server.mothercare.entities.Post;
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
    public boolean update(Post thePost) {
        return postDAO.save(thePost);
    }

    @Override
    @Transactional
    public List<Post> getPosts() {
        return postDAO.getPosts();
    }
}
