package com.yallahnsafro.yallahnsafrobackend.services.implimentation;

import com.yallahnsafro.yallahnsafrobackend.repositories.TripRepository;
import com.yallahnsafro.yallahnsafrobackend.services.TripService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TripServiceImp implements TripService {

    @Override
    public TripDto createTrip(TripDto tripDto) {

        return null;
    }

    @Override
    public TripDto getTripById(long tripId) {
        return null;
    }

    @Override
    public TripDto updateTrip(TripDto tripDto) {
        return null;
    }

    @Override
    public void deleteTrip(TripDto tripDto) {

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
