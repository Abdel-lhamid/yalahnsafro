package com.yallahnsafro.yallahnsafrobackend.repositories;
import com.yallahnsafro.yallahnsafrobackend.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends PagingAndSortingRepository<TripEntity, Long> {


}
