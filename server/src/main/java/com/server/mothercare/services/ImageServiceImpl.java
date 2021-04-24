package com.server.mothercare.services;

import com.server.mothercare.DAOs.ImageRepository;
import com.server.mothercare.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional
    public Image save(Image theImage) {
        theImage.setId(0);
        return imageRepository.save(theImage);
    }

    @Override
    @Transactional
    public Image update(Image theImage) {
        return imageRepository.save(theImage);
    }

    @Override
    @Transactional
    public List<Image> getImages() {
        return imageRepository.findAll();
    }
}
