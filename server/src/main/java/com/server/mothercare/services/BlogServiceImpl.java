package com.server.mothercare.services;

import com.server.mothercare.DAOs.BlogDAO;
import com.server.mothercare.entities.Blog;
import com.server.mothercare.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogDAO blogDAO;
    @Override
    @Transactional
    public boolean save(Blog theBlog) {
        return blogDAO.save(theBlog);
    }

    @Override
    @Transactional
    public boolean saveComment(Comment theComment) {
        return blogDAO.saveComment(theComment);
    }

    @Override
    @Transactional
    public List<Blog> getBlogs(int cycle) {
        return blogDAO.getBlogs(cycle);
    }

    @Override
    @Transactional
    public Blog getBlogById(int theId) {
        return blogDAO.getBlogById(theId);
    }
}
