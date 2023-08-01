package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;

import java.util.List;

public interface TripService {

    TripDto createTrip(TripDto tripDto);

    TripDto getTripById(long tripId);

    TripDto updateTrip(TripDto tripDto);

    void deleteTrip(TripDto tripDto);

    List<TripDto> getAllTrips();

    List<TripDto> getAllActiveTrips();


}
