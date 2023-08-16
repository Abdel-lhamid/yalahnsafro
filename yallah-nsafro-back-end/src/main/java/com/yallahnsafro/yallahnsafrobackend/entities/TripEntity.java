package com.yallahnsafro.yallahnsafrobackend.entities;

import com.yallahnsafro.yallahnsafrobackend.shared.TripStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name = "depart_id", referencedColumnName = "id")
    private DepartEntity depart;
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private DestinationEntity destination;
    @Column(nullable = false)
    private float price;
    @Column
    private float discountedPrice;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TripStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private UserEntity organizer;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripImageEntity> images;
    @OneToMany(mappedBy = "trip")
    private List<BookingEntity> bookings;
    private LocalDateTime tripDate;
    private LocalDateTime reservationEndDate;










}
