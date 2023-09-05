package com.yallahnsafro.yallahnsafrobackend.services.implimentation;

import com.yallahnsafro.yallahnsafrobackend.entities.BookingEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.BookingRepository;
import com.yallahnsafro.yallahnsafrobackend.services.BookingService;
import com.yallahnsafro.yallahnsafrobackend.shared.BookingStatus;
import com.yallahnsafro.yallahnsafrobackend.shared.Utils;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.BookingDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BookingServiceImpl implements BookingService {


    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Utils util;


    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        //Create a booking entity
        BookingEntity bookingEntity = modelMapper.map(bookingDto, BookingEntity.class);
        //Set the booking entity status to pending
        bookingEntity.setStatus(BookingStatus.REQUESTED);
        //Set the booking entity bookingId to a random string
        bookingEntity.setBookingId(util.generateCustomId(30));
        //save the booking entity
        BookingEntity bookingCreated = bookingRepository.save(bookingEntity);
        //map the booking entity to a bookingDto
        BookingDto bookingCreatedDto = modelMapper.map(bookingCreated, BookingDto.class);
        //return the bookingDto
        return bookingCreatedDto;
    }

}
