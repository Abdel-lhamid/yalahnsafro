package com.yallahnsafro.yallahnsafrobackend.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings", schema = "yallahnsafro_db")
@Data
@NoArgsConstructor
public class BookingEntity implements Serializable {
    private static final long serialVersionUID = 6407689834561559517L;

    @Id
    @GeneratedValue
    private Long id;

    private String bookingId;

    @CreationTimestamp
    private LocalDateTime bookingDate;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity customer;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private TripEntity trip;





}
