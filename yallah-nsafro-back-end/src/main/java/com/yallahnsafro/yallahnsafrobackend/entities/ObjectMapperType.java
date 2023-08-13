package com.yallahnsafro.yallahnsafrobackend.entities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperType extends ObjectMapper {



    public ObjectMapperType() {
        this.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }
}
