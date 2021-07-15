package com.server.mothercare.DAOs;

import com.server.mothercare.entities.post.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
}
