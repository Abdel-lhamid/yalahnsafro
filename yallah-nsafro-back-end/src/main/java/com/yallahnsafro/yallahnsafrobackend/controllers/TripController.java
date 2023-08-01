package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.services.TripService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    //@Autowired(required=true)
    //TripService tripService;


    @GetMapping()
    public String getAllTrips(){
        //List<TripDto> allTrips = new ArrayList<>();
       // allTrips = tripService.getAllTrips();
        return null;
    }
    @GetMapping("/{tripId}")
    public String getTripById(@PathVariable long tripId){
        //TripDto tripDtoById = new TripDto();
        //tripDtoById = tripService.getTripById(tripId);
        return null;
    }

    @PostMapping()
    public String createTrip(@RequestBody TripDto tripToCreate){
        //TripDto newTrip = tripService.createTrip(tripToCreate);
        return null;
    }
    @DeleteMapping()
    public String deleteTrip(@RequestBody TripDto tripDto){
        return null;
    }
    @PutMapping()
    public String updateTrip(@RequestBody TripDto tripUpdated){
        return null;
    }


}