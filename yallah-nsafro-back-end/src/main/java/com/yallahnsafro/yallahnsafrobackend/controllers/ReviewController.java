package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.services.ReviewService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/createReview")
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto){
        ReviewDto reviewCreated = new ReviewDto();
        reviewCreated = reviewService.createReview(reviewDto);
        return reviewCreated;
    }

    @GetMapping("/getReviewById/{reviewId}")
    public ReviewDto getReviewById(@PathVariable long reviewId){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto = reviewService.getReviewById(reviewId);
        return reviewDto;
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable long reviewId){
        reviewService.deleteReview(reviewId);
    }
    @GetMapping
    public List<ReviewDto> getReviews(long tripId){
        List<ReviewDto> reviewsDto = new ArrayList<>();
        //reviewsDto = reviewService.getAllReviewsByTrip(page, limit, tripId);
        return reviewsDto;
    }




}
