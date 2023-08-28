package com.yallahnsafro.yallahnsafrobackend.requests;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.ImageDto;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.TripDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TripRequest {
    TripDto tripDto;
    List<ImageDto> images;
}
