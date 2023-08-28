package com.yallahnsafro.yallahnsafrobackend.shared.dto;

import com.yallahnsafro.yallahnsafrobackend.entities.TripEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    private long id;
    private String url;
    private boolean isMain;
    private TripDto trip;

}
