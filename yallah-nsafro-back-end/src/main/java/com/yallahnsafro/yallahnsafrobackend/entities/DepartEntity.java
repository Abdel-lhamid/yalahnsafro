package com.yallahnsafro.yallahnsafrobackend.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class DepartEntity {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "depart")
    private List<TripEntity> trips;
}
