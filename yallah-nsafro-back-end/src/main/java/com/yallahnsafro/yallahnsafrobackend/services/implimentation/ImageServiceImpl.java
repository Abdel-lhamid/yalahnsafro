package com.yallahnsafro.yallahnsafrobackend.services.implimentation;

import com.yallahnsafro.yallahnsafrobackend.repositories.ImageRepository;
import com.yallahnsafro.yallahnsafrobackend.services.ImageService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public ImageDto createImage(ImageDto imageDto) {
       // ImageDto imageCreated = imageRepository.save(imageDto);


        return null;
    }
}
