package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, Integer> {

}
