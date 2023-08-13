package com.yallahnsafro.yallahnsafrobackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "trips", schema = "yallahnsafro_db")
@Getter
@Setter
public class TripEntity implements Serializable {
    private static final long serialVersionUID = 6407688734561559517L;

    @Id
    @GeneratedValue
    private long id;
    @Column (nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 5000)
    private String description;
    @Column(nullable = false)
    private String departure;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private String thumbNailImage;

    @Column
    private String organizer_Id;

    /**
     * @OneToMany (cascade = CascadeType.ALL)
     * @JoinColumn (name = "trip_id", referencedColumnName = "id")
     * private List<BookingEntity> bookings;
     *
     */


}
