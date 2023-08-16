package com.yallahnsafro.yallahnsafrobackend.repositories;

import com.yallahnsafro.yallahnsafrobackend.entities.ReviewEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {


}
