package com.yallahnsafro.yallahnsafrobackend.repositories;
import com.yallahnsafro.yallahnsafrobackend.entities.TripEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository <TripEntity, Long> {
}
