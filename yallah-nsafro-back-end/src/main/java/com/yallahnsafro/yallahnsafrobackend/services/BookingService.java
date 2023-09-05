package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.BookingDto;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);
}
