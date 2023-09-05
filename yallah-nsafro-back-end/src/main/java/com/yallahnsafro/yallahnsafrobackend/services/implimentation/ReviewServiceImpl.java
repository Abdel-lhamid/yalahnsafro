package com.yallahnsafro.yallahnsafrobackend.services.implimentation;

import com.yallahnsafro.yallahnsafrobackend.entities.ReviewEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.ReviewRepository;
import com.yallahnsafro.yallahnsafrobackend.services.ReviewService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.ReviewDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    //function that create a review in database
    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        BeanUtils.copyProperties(reviewDto, reviewEntity);
        ReviewEntity reviewCreated = reviewRepository.save(reviewEntity);
        BeanUtils.copyProperties(reviewCreated, reviewDto);
        return reviewDto;
    }

    @Override
    public ReviewDto getReviewById(long reviewId) throws UsernameNotFoundException{
        ReviewDto reviewDto = new ReviewDto();
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElse(null);
        if (reviewEntity == null) throw new UsernameNotFoundException("Review with id: " + reviewId + " not found");
        BeanUtils.copyProperties(reviewEntity, reviewDto);
        return reviewDto;
    }

    @Override
    public void deleteReview(long reviewId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElse(null);
        if (reviewEntity == null) throw new UsernameNotFoundException("Review with id: " + reviewId + " not found");
        reviewRepository.delete(reviewEntity);
    }

    /*@Override
    public List<ReviewDto> getAllReviewsByTrip(int page, int limit, long tripId) {
        //function that fetchs all reviews by trip
        if (page > 0) page -= 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<ReviewEntity> reviews = reviewRepository.findByTrip(tripId, pageableRequest);
        List<ReviewDto> reviewsDto = null;
        for (ReviewEntity reviewEntity : reviews){
            ReviewDto reviewDto = new ReviewDto();
            BeanUtils.copyProperties(reviewEntity, reviewDto);
            reviewsDto.add(reviewDto);
        }
        return reviewsDto;
    }
    */

    /*@Override
    public Double getTripReviewStar(long tripId) {
        List<ReviewEntity> reviews = reviewRepository.getTripReviewStars(tripId);
        //calculate the average of the stars
        if (reviews.size() > 0) {
            double sum = 0;
            for (ReviewEntity reviewEntity : reviews) {
                sum += reviewEntity.getStars();
            }
            return (sum / reviews.size());
        }
        return (double) 0;

    }*/


}
