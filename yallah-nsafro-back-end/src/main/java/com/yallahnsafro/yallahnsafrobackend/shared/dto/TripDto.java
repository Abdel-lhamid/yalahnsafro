package com.yallahnsafro.yallahnsafrobackend.shared.dto;

import com.yallahnsafro.yallahnsafrobackend.shared.TripStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TripDto implements Serializable {
    private static final long serialVersionUID = 6407688734661549517L;

    private long id;
    private String tripId;
    private String title;
    private String description;
    private int availableSeats;
    private DepartDto depart;
    private DestinationDto destination;
    private float price;
    private float discountedPrice;
    @Enumerated(EnumType.STRING)
    private TripStatus status;
    private LocalDateTime createdAt;
    private UserDto organizer;
    private List<TripImageDto> images;
    private List<BookingDto> bookings;
    private LocalDateTime tripDate;
    private LocalDateTime reservationEndDate;


}
