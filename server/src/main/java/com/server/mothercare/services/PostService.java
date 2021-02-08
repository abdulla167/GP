package com.server.mothercare.services;

import com.server.mothercare.entities.Post;

import java.util.List;

public interface PostService {
    public boolean save (Post thePost);
    public boolean update (Post thePost);
    public List<Post> getPosts();
}
