package com.yallahnsafro.yallahnsafrobackend.entities;

import com.yallahnsafro.yallahnsafrobackend.shared.TripStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trips", schema = "yallahnsafro_db")
@Data
@NoArgsConstructor
public class TripEntity implements Serializable {
    private static final long serialVersionUID = 6407688734561559517L;


    @Id
    @GeneratedValue
    private long id;
    @Column (nullable = false)
    private String tripId;
    @Column (nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 5000)
    private String description;
    @Column(nullable = false)
    private int availableSeats;

    private String depart;

    private String destination;

    private String activities;
    @Column
    private double price;
    @Column
    private double discountedPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private UserEntity organizer;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<ImageEntity> images;
    @OneToMany(mappedBy = "trip")
    private List<BookingEntity> bookings;
    private Date tripStartDate;
    private Date tripEndDate;
    private Date dateFinReservation;










}
