package com.desper.mapper;

import com.desper.core.Hotel;
import com.desper.dto.CityDto;
import com.desper.dto.HotelDto;
import com.desper.dto.LocationDto;
import com.desper.dto.MainPictureDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
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
        cityDto.setId(entity.getCityId());
        locationDto.setCityDto(cityDto);

        ObjectMapper mapper = new ObjectMapper();
        MainPictureDto mainPictureDto= null;

        try {
            mainPictureDto = mapper.readValue(entity.getMain_picture(), MainPictureDto.class);
        } catch (IOException e) {
            LOGGER.error("Error parsing json information "+e.getMessage(),e);
        }

        HotelDto hotelDto = new HotelDto(
                entity.getId(),
                entity.getName(),
                entity.getCityId(),
                mainPictureDto,
                null);
        return hotelDto;
    }
}

