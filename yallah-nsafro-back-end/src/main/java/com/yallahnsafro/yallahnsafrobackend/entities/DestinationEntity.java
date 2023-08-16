package com.yallahnsafro.yallahnsafrobackend.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class DestinationEntity {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "destination")
    private List<TripEntity> trips;
}
