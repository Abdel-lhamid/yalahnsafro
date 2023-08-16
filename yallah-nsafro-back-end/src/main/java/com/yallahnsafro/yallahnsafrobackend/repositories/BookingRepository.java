package com.yallahnsafro.yallahnsafrobackend.repositories;

import com.yallahnsafro.yallahnsafrobackend.entities.BookingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {
}
