package com.yallahnsafro.yallahnsafrobackend.services.implimentation;

import com.yallahnsafro.yallahnsafrobackend.entities.TripEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.TripRepository;
import com.yallahnsafro.yallahnsafrobackend.services.TripService;
import com.yallahnsafro.yallahnsafrobackend.shared.Utils;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    Utils util;

    @Autowired
    TripRepository tripRepository;


    @Override
    public TripDto createTrip(TripDto tripDto) {


        return null;
    }

    //create a function getTripById in TripService and TripServiceImpl that takes a long tripId as parameter and returns a TripDto, it finds the trip by id
    @Override
    public TripDto getTripById(long tripId) {
        TripEntity tripEntity = new TripEntity();
        tripEntity = tripRepository.findById(tripId).orElse(null);
        if (tripEntity != null){
            TripDto tripDto = new TripDto();
            BeanUtils.copyProperties(tripEntity, tripDto);
            return tripDto;

        }
        return  null;
    }

    //create function that updates a trip
    @Override
    public TripDto updateTrip(TripDto tripDto)throws RuntimeException {
        //find the trip by id
        TripEntity tripEntity = new TripEntity();
        tripEntity = tripRepository.findById(tripDto.getId()).orElse(null);
        //update the trip
        if (tripEntity != null){
            TripEntity tripToUpdate = new TripEntity();
            BeanUtils.copyProperties(tripDto, tripToUpdate);
            TripEntity tripUpdated = tripRepository.save(tripToUpdate);
            TripDto tripDtoUpdated = new TripDto();
            BeanUtils.copyProperties(tripUpdated, tripDtoUpdated);
            return tripDtoUpdated;
        }else {
            throw new RuntimeException("Trip not found");
        }
    }

    @Override
    public boolean deleteTrip(TripDto tripDto) {
        //function that deletes a trip
        //find the trip by id first and then delete it
        TripEntity tripEntity = new TripEntity();
        tripEntity = tripRepository.findById(tripDto.getId()).orElse(null);
        if (tripEntity != null) {
            tripRepository.delete(tripEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<TripDto> getAllTrips() {
        return null;
    }

    @Override
    public List<TripDto> getAllActiveTrips() {
        return null;
    }


}
