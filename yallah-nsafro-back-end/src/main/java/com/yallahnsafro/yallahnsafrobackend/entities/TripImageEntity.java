package com.yallahnsafro.yallahnsafrobackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "trip_images", schema = "yallahnsafro_db")
@Data
@NoArgsConstructor
public class TripImageEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;


    private String imageUrl;


    private boolean isImageMain;


    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;





}
