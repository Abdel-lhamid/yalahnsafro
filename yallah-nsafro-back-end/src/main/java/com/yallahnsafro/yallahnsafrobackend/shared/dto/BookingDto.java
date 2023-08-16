package com.yallahnsafro.yallahnsafrobackend.shared.dto;

import com.yallahnsafro.yallahnsafrobackend.shared.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class BookingDto implements Serializable {

    private long id;

    private String bookingId;

    private BookingStatus status;

    private LocalDateTime bookingDate;

    private UserDto customer;

    private TripDto trip;

    private List<ReviewDto> reviews;

}
