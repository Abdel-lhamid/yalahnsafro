package com.yallahnsafro.yallahnsafrobackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
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
    private String departure;


    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Double price;


    @Column(nullable = true)
    private String thumbNailImageUrl;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private UserEntity organizer;


    @OneToMany(mappedBy = "trip")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "trip")
    private List<ReviewEntity> reviews;










    /**
     * @OneToMany (cascade = CascadeType.ALL)
     * @JoinColumn (name = "trip_id", referencedColumnName = "id")
     * private List<BookingEntity> bookings;
     *
     */


}
