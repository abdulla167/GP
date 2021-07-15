package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image,Integer> {
}
