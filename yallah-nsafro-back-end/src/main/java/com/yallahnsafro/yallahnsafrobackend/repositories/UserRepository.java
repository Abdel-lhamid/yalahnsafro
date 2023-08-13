package com.yallahnsafro.yallahnsafrobackend.repositories;
import com.yallahnsafro.yallahnsafrobackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);


}
