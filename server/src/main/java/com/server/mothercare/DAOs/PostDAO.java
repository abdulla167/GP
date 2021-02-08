package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Post;

import java.util.List;

public interface PostDAO {
    public boolean save (Post thePost);
    public List<Post> getPosts();
}
