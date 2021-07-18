package com.server.mothercare.services;


import com.server.mothercare.entities.User;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.SavedBlog;

import java.util.List;

public interface BlogService {
    public Blog save(Blog theBlog);
    public Blog update(Blog theBlog);
    public void deleteById(int theId);
    public List<Blog> getBlogs(int cycle);
    public Blog getBlogById(int theId);


}
