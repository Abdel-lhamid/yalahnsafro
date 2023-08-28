package com.yallahnsafro.yallahnsafrobackend.services.implimentation;

import com.yallahnsafro.yallahnsafrobackend.entities.ImageEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.ImageRepository;
import com.yallahnsafro.yallahnsafrobackend.services.ImageService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.ImageDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    //create a function the saves an image
    @Override
    public ImageDto createImage(ImageDto imageDto) {
        ImageEntity imageEntity = new ImageEntity();
        BeanUtils.copyProperties(imageDto, imageEntity);
        ImageEntity imageCreated = imageRepository.save(imageEntity);
        ImageDto imageDtoCreated = new ImageDto();
        BeanUtils.copyProperties(imageCreated, imageDtoCreated);
        return imageDtoCreated;
    }


}
