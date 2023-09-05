package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.services.ImageService;
import com.yallahnsafro.yallahnsafrobackend.services.TripService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    @Autowired
    TripService tripService;


    @PostMapping("/createTrip")
    public TripDto createTrip(@RequestBody TripDto tripToCreate, Principal principal){
        String userEmail = principal.getName();
        TripDto tripCreated = tripService.createTrip(tripToCreate, userEmail);

        return tripCreated;
    }

    @GetMapping()
    public String getAllTrips(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int limit){
        List<TripDto> allTrips = new ArrayList<>();
        allTrips = tripService.getAllTrips(page, limit);
        return null;
    }
    @GetMapping("/{tripId}")
    public TripDto getTripById(@PathVariable long tripId){
        TripDto tripDtoById = new TripDto();
        tripDtoById = tripService.getTripById(tripId);
        return tripDtoById;
    }


    @DeleteMapping()
    public boolean deleteTrip(@RequestBody TripDto tripDto){
        boolean isDeleted = tripService.deleteTrip(tripDto);
        return isDeleted;
    }
    @PutMapping()
    public TripDto updateTrip(@RequestBody TripDto tripUpdated){
        TripDto tripDto = tripService.updateTrip(tripUpdated);

        return tripDto;
    }


}