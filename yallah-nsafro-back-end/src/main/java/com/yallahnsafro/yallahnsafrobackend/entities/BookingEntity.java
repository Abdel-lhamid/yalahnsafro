package com.yallahnsafro.yallahnsafrobackend.entities;


import com.yallahnsafro.yallahnsafrobackend.shared.BookingStatus;
import com.yallahnsafro.yallahnsafrobackend.shared.UserRole;
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
    private long id;

    private String bookingId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @CreationTimestamp
    private LocalDateTime bookingDate;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity customer;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private TripEntity trip;

    @OneToMany(mappedBy = "booking")
    private List<ReviewEntity> reviews;





}
