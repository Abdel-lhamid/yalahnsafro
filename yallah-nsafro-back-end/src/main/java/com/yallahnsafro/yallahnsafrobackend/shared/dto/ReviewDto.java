package com.yallahnsafro.yallahnsafrobackend.shared.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ReviewDto implements Serializable {

    private long id;
    private String reviewId;
    private String reviewText;
    private int stars;
    private BookingDto booking;
    private UserDto customer;




}
