package com.yallahnsafro.yallahnsafrobackend.shared.dto;

import com.yallahnsafro.yallahnsafrobackend.shared.TripStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TripDto implements Serializable {
    private static final long serialVersionUID = 6407688734661549517L;

    private long id;
    private String tripId;
    private String title;
    private String description;
    private int availableSeats;
    private String depart;
    private String destination;
    private double price;
    private double discountedPrice;
    private TripStatus status;
    private LocalDateTime createdAt;
    private String organizer_id;
    private List<ImageDto> images;
    private List<BookingDto> bookings;
    private Date tripStartDate;
    private Date tripEndDate;
    private Date dateFinReservation;


}
