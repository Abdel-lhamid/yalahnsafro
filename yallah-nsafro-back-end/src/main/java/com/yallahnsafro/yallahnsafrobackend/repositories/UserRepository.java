package com.yallahnsafro.yallahnsafrobackend.repositories;

import com.yallahnsafro.yallahnsafrobackend.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <UserEntity, Long>{



}
