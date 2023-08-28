package com.yallahnsafro.yallahnsafrobackend.repositories;

import com.yallahnsafro.yallahnsafrobackend.entities.ImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, Long> {
}
