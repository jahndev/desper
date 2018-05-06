package com.destinder.controller;

import com.destinder.api.HotelDto;
import com.destinder.core.Hotel;
import com.destinder.dao.HotelDao;
import com.destinder.mapper.HotelMapper;
import com.destinder.service.HotelService;
import org.slf4j.Logger;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class HotelController extends BaseController<HotelDto, Hotel>{

    private final Logger logger = getLogger(getClass());
    private final HotelMapper hotelMapper;
    private final HotelDao hotelDao;
    private final HotelService hotelService;

   @Inject
    public HotelController(HotelMapper mapper,HotelDao hotelDao, HotelService hotelService){
        super(hotelDao,mapper);
        this.hotelMapper = mapper;
        this.hotelDao = hotelDao;
        this.hotelService = hotelService;
    }

    public List<HotelDto> findHotelsByCityIds(HotelDto hotelDto){
        Stream<HotelDto> hotelsStream = hotelService.getHotels()
                .stream()
                .filter( h -> h.getLocation().getCityDto().getId()
                .equals(hotelDto.getLocation().getCityDto().getId()));

        return hotelsStream.collect(Collectors.toList());
    }

    public HotelDto findHotelByCityId(Long city_id){
        /*Optional<HotelDto> phoneOptional = phoneDao.getPhoneByNumberPhone(phoneDto.getNumber());
        phoneOptional.orElseThrow(EntityNotFoundException::new);
        return phoneMapper.createApiDto(phoneOptional.get());*/

        return null;
    }
}
