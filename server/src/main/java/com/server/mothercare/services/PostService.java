package com.server.mothercare.services;

import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.entities.post.Like;
import com.server.mothercare.entities.post.Post;
import com.server.mothercare.entities.User;

import java.util.List;

public interface PostService {
    public boolean save (Post thePost);
    public boolean saveComment (Comment theComment);
    public boolean update (Post thePost);
    public boolean updateComment (Comment theComment);
    public List<Post> getPosts(int cycle);
    Post getPostById(int theId);
    public boolean addLike(Like theLike);
    public List<Post> likedPosts(User theUser);
}
