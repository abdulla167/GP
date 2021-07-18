package com.server.mothercare.DAOs;

import com.server.mothercare.entities.post.Blog;

import java.util.List;

public interface BlogRepositoryCustom {
    public List<Blog> getBlogs(int cycle);
}