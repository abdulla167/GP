package com.server.mothercare.DAOs;

import com.server.mothercare.entities.post.Blog;

import org.springframework.data.repository.CrudRepository;


public interface BlogRepository extends CrudRepository<Blog, Integer>, BlogRepositoryCustom{

}