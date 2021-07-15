package com.server.mothercare.services;

import com.server.mothercare.DAOs.CommentRepository;
import com.server.mothercare.entities.post.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Override
    @Transactional
    public Comment save(Comment theComment) {
        theComment.setId(0);
        return commentRepository.save(theComment);
    }

    @Override
    public Comment update(Comment theComment) {
        return commentRepository.save(theComment);
    }


    @Override
    @Transactional
    public Comment getCommentById(int theId) {
        Optional<Comment> output = commentRepository.findById(theId);
        Comment comment = null ;
        if (output.isPresent()){
            comment = output.get();
        }
        return  comment;
    }
}
