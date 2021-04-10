package com.server.mothercare.DAOs;

import com.server.mothercare.entities.Image;
import com.server.mothercare.entities.Post;

import java.util.List;

public interface ImageDAO {
    public boolean save (Image theImage);
    public List<Image> getImages();
}
