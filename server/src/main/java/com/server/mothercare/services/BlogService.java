package com.server.mothercare.services;


import com.server.mothercare.entities.post.Blog;
import java.util.List;

public interface BlogService {
    public Blog save(Blog theBlog);
    Blog update(Blog theBlog);
    public List<Blog> getBlogs(int cycle);
    Blog getBlogById(int theId);

//    public boolean addLike(Like theLike);
//    public List<Post> likedPosts(User theUser);
//    public boolean addCommentToComment(Comment theComment);

}
