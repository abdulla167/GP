package com.server.mothercare.services;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;

import java.util.List;

public interface BlogService {
    public boolean save (Blog theBlog);
    public boolean saveComment (Comment theComment);
    //    public Comment getCommentById(int theId);
    public List<Blog> getBlogs(int cycle);
    Blog getBlogById(int theId);
//    public boolean addLike(Like theLike);
//    public List<Post> likedPosts(User theUser);
//    public boolean addCommentToComment(Comment theComment);

}
