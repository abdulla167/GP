package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image,Integer> {
}
