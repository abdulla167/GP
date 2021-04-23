package com.server.mothercare.services;

import com.server.mothercare.entities.Image;

import java.util.List;

public interface ImageService {
    public boolean save (Image theImage);
    public boolean update (Image theImage);
    public List<Image> getImages();
}
