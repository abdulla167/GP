package com.server.mothercare.services;


import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;

import java.util.List;

public interface CommentService {
    public Comment save(Comment theComment);
    Comment update(Comment theComment);
    Comment getCommentById(int theId);


}
