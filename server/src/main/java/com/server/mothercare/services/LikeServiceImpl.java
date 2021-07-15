package com.server.mothercare.services;

import com.server.mothercare.DAOs.LikeRepository;
import com.server.mothercare.entities.post.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    LikeRepository likeRepository;
    @Override
    @Transactional
    public Like save(Like theLike) {
        theLike.setId(0);
        return likeRepository.save(theLike);
    }

    @Override
    @Transactional
    public Like update(Like theLike) {
        return likeRepository.save(theLike);
    }

    @Override
    @Transactional
    public Optional<Like> getLikeById(int theId) {
        return likeRepository.findById(theId);
    }

    @Override
    @Transactional
    public boolean deleteLike(int theId) {
        boolean deleted = false;
        try {
            likeRepository.deleteById(theId);
            deleted = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return deleted;
    }
}
