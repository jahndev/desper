package com.destinder.mapper;

import com.destinder.api.*;
import com.destinder.core.Hotel;
import com.destinder.util.ApiDesperClient.DespegarClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class HotelMapper implements Mapper<HotelDto, Hotel>{

    private static final Logger LOGGER = Logger.getLogger(HotelMapper.class);

    public HotelMapper() {}

    @Override
    public void updateEntity(Hotel entity, HotelDto dto) {}

    @Override
    public Hotel createEntity(Class<?> dtoClass) {
        return new Hotel();
    }

    @Override
    public HotelDto createApiDto(Hotel entity) {
        LocationDto locationDto = new LocationDto();

        CityDto cityDto = new CityDto();
        cityDto.setId(entity.getCit_id());
        locationDto.setCityDto(cityDto);

        ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MainPictureDto mainPictureDto= null;

        try {
            mainPictureDto = mapper.readValue(entity.getMain_picture(), MainPictureDto.class);
        } catch (IOException e) {
            LOGGER.error("Error parsing json information "+e.getMessage(),e);
        }

        return new HotelDto(
            entity.getId(),
            entity.getName(),
            entity.getStars(),
            locationDto,
            mainPictureDto
        );
    }
}

