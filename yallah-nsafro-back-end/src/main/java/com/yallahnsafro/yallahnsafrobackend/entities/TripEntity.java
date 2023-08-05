package com.yallahnsafro.yallahnsafrobackend.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "trips", schema = "yallahnsafro_db")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getThumbNailImage() {
        return thumbNailImage;
    }

    public void setThumbNailImage(String thumbNailImage) {
        this.thumbNailImage = thumbNailImage;
    }

    public String getOrganizer_Id() {
        return organizer_Id;
    }

    public void setOrganizer_Id(String organizer_Id) {
        this.organizer_Id = organizer_Id;
    }
}
