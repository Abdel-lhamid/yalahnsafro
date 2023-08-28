package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.services.ImageService;
import com.yallahnsafro.yallahnsafrobackend.services.TripService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/trips")
public class TripController {
    @Autowired
    TripService tripService;
    @Autowired
    ImageService imageService;




    @PostMapping("/createTrip")
    public TripDto createTrip(@RequestBody TripDto tripToCreate, Principal principal){
        String userEmail = principal.getName();
        TripDto tripCreated = tripService.createTrip(tripToCreate, userEmail);

        return tripCreated;
    }

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


    @DeleteMapping()
    public String deleteTrip(@RequestBody TripDto tripDto){
        return null;
    }
    @PutMapping()
    public String updateTrip(@RequestBody TripDto tripUpdated){
        return null;
    }


}