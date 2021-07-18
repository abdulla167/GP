package com.server.mothercare.DAOs;

import com.server.mothercare.entities.post.SavedBlog;
import org.springframework.data.repository.CrudRepository;

public interface SavedBlogDAO extends CrudRepository<SavedBlog,Long>, SavedBlogDAOCustom {
}
