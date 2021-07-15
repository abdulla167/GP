package com.server.mothercare.DAOs;

import com.server.mothercare.entities.post.Like;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, Integer> {

}
