package com.server.mothercare.services;

import com.server.mothercare.DAOs.BlogRepository;
import com.server.mothercare.entities.post.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog save(Blog theBlog) {
        theBlog.setId(0);
        return blogRepository.save(theBlog);
    }

    @Override
    @Transactional
    public Blog update(Blog theBlog) {
        return blogRepository.save(theBlog);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        blogRepository.deleteById(theId);
    }


    @Override
    @Transactional
    public List<Blog> getBlogs(int cycle, String user, String category) {
        return blogRepository.getBlogs(cycle, user, category);
    }

    @Override
    @Transactional
    public Blog getBlogById(int theId) {
        Optional<Blog> output = blogRepository.findById(theId);
        Blog blog = null ;
        if (output.isPresent()){
            blog = output.get();
        }
        return  blog;
    }
    @Override
    @Transactional
    public List<Blog> getUserBlogs(String userName) {
        return blogRepository.getUserBlogs(userName);
    }
    @Override
    @Transactional
    public long blogsCount(String author, String category) {
        return blogRepository.blogsCount(author, category);
    }

}
