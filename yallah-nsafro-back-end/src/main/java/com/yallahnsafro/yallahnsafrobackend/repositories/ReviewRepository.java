package com.yallahnsafro.yallahnsafrobackend.repositories;

import com.yallahnsafro.yallahnsafrobackend.entities.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<ReviewEntity, Long> {

    //List<ReviewEntity> findByTrip(long tripId, Pageable pageable);

   /* @Query(value = "SELECT * FROM reviews WHERE trip_id = ?1", nativeQuery = true)
    List<ReviewEntity> getTripReviewStars(long tripId);*/

}
