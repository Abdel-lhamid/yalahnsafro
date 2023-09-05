package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);

    ReviewDto getReviewById(long reviewId);

    void deleteReview(long reviewId);

    //List<ReviewDto> getAllReviewsByTrip(int page, int limit, long tripId);

   // Double getTripReviewStar(long tripId);
}
