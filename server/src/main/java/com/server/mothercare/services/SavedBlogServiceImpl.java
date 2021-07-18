package com.server.mothercare.services;

import com.server.mothercare.DAOs.SavedBlogDAO;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.SavedBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SavedBlogServiceImpl implements SavedBlogService{

    @Autowired
    private SavedBlogDAO savedBlogDao;

    @Override
    @Transactional
    public boolean bommarkBlog(SavedBlog savedBlog) {
        boolean saved = false;
        try {
            savedBlog.setId(0);
            savedBlogDao.save(savedBlog);
            saved = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return saved;
    }

    @Override
    @Transactional
    public boolean unBommarkBlog(SavedBlog savedBlog) {
        boolean deleted = false;
        try {
            savedBlogDao.delete(savedBlog);
            deleted = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    @Override
    @Transactional
    public List<Blog> userBommarks(String userName) {
        return savedBlogDao.getUserBommarks(userName);
    }
}
