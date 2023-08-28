package com.yallahnsafro.yallahnsafrobackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "trip_images", schema = "yallahnsafro_db")
@Data
@NoArgsConstructor
public class ImageEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String url;

    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private TripEntity trip;



}
