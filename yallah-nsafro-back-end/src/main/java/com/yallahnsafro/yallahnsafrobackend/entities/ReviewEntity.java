package com.yallahnsafro.yallahnsafrobackend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "reviews", schema = "yallahnsafro_db")
@NoArgsConstructor
public class ReviewEntity implements Serializable {
    private static final long serialVersionUID = 6407688734561529517L;

    @Id
    @GeneratedValue
    private long id;

    private String reviewId;

    private String reviewText;

    private int stars;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private BookingEntity booking;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity customer;






}
