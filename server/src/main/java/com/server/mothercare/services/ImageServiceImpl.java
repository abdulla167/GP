package com.server.mothercare.services;

import com.server.mothercare.DAOs.ImageDAO;
import com.server.mothercare.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageDAO imageDAO;

    @Override
    @Transactional
    public boolean save(Image theImage) {
        theImage.setId(0);
        return imageDAO.save(theImage);
    }

    @Override
    @Transactional
    public boolean update(Image theImage) {
        return imageDAO.save(theImage);
    }

    @Override
    @Transactional
    public List<Image> getImages() {
        return imageDAO.getImages();
    }
}
