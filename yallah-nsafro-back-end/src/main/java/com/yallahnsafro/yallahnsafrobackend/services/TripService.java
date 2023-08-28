package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;

import java.util.List;

public interface TripService {

    TripDto createTrip(TripDto tripDto, String userEmail);

    TripDto getTripById(long tripId);

    TripDto updateTrip(TripDto tripDto);

    boolean deleteTrip(TripDto tripDto);

    List<TripDto> getAllTrips();

    List<TripDto> getAllActiveTrips();



}
