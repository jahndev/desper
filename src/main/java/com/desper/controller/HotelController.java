package com.desper.controller;

import com.desper.core.HotelEvaluation;
import com.desper.dao.HotelEvaluationDAO;
import com.desper.dto.HotelDto;
import com.desper.core.Hotel;
import com.desper.dao.HotelDAO;
import com.desper.dto.HotelEvaluationDto;
import com.desper.mapper.HotelMapper;
import com.desper.service.HotelService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class HotelController extends BaseController<HotelDto, Hotel>{

    private final Logger logger = getLogger(getClass());
    private final HotelMapper hotelMapper;
    private final HotelDAO hotelDao;
    private final HotelEvaluationDAO hotelEvaluationDAO;
    private final HotelService hotelService;

    @Inject
    public HotelController(HotelMapper mapper,HotelDAO hotelDao, HotelService hotelService,HotelEvaluationDAO hotelEvaluationDAO){
        super(hotelDao,mapper);
        this.hotelMapper = mapper;
        this.hotelDao = hotelDao;
        this.hotelService = hotelService;
        this.hotelEvaluationDAO = hotelEvaluationDAO;
    }

    public Optional<List<HotelDto>> findHotelsByCityIds(Long cityId){
        List<HotelDto> hotelsStream = hotelService.getHotels().stream().filter(
                h -> h.getLocation().getCityDto().getId().equals(cityId) )
                .collect(Collectors.toList());

        loadPriceByHotel(hotelsStream);

        return Optional.of(hotelsStream);
    }

    private List<HotelDto> loadPriceByHotel(List<HotelDto> hotelDtos){
        return  hotelService.getDespegarApi().loadPriceByHotelId(hotelDtos);
    }

    public void setEvaluation(HotelEvaluationDto hotelEvaluationDto) throws Exception {

        if(hotelDao.findById(hotelEvaluationDto.getHotelId()).isPresent()){

            Optional<HotelEvaluation> hotelEvaluation =
                    hotelEvaluationDAO.findByHotelAndUser(
                            hotelEvaluationDto.getHotelId(),
                            hotelEvaluationDto.getUserName());

            if(hotelEvaluation.isPresent()){
                hotelEvaluation.get().setDhe_evaluation(hotelEvaluationDto.getEvaluation());
                hotelEvaluationDAO.update(hotelEvaluation.get());

            }else{
                HotelEvaluation newHotelEvaluation = new HotelEvaluation(
                        hotelEvaluationDto.getHotelId(),
                        hotelEvaluationDto.getUserName(),
                        hotelEvaluationDto.getEvaluation()
                );
                hotelEvaluationDAO.create(newHotelEvaluation);
            }
        }else{
            throw new Exception("Hotel Not Found");
        }
    }

    public void updateEvaluation(HotelEvaluationDto hotelEvaluationDto) {

        Optional<HotelEvaluation> hotelEvaluation =
                hotelEvaluationDAO.findByHotelAndUser(
                        hotelEvaluationDto.getHotelId(),
                        hotelEvaluationDto.getUserName());

        if(hotelEvaluation.isPresent()){
            hotelEvaluation.get().setDhe_evaluation(hotelEvaluationDto.getEvaluation());
            hotelEvaluationDAO.update(hotelEvaluation.get());
        }
    }

    public void deleteEvaluation(HotelEvaluationDto hotelEvaluationDto){
        Optional<HotelEvaluation> hotelEvaluation =
                hotelEvaluationDAO.findByHotelAndUser(
                        hotelEvaluationDto.getHotelId(),
                        hotelEvaluationDto.getUserName());

        if(hotelEvaluation.isPresent()){
            hotelEvaluationDAO.delete(hotelEvaluation.get());
        }
    }

    public Optional<List<HotelEvaluationDto>> findEvaluationsByHotel(HotelEvaluationDto hotelEvaluationDto){

        List<HotelEvaluationDto> hotelEvaluations = new ArrayList<>();
        hotelEvaluationDAO.findByHotel(hotelEvaluationDto.getHotelId()).get().forEach(
                eval -> hotelEvaluations.add(
                        new HotelEvaluationDto(eval.getDhe_hotel_id(),
                                eval.getDhe_user_name(),
                                eval.getDhe_evaluation())
                )
        );

        return Optional.of(hotelEvaluations);
    }

    public Optional<List<HotelEvaluationDto>> findEvaluationsByUser(HotelEvaluationDto hotelEvaluationDto){

        List<HotelEvaluationDto> hotelEvaluations = new ArrayList<>();
        hotelEvaluationDAO.findByUser(hotelEvaluationDto.getUserName()).get().forEach(
                eval -> hotelEvaluations.add(
                        new HotelEvaluationDto(eval.getDhe_hotel_id(),
                                eval.getDhe_user_name(),
                                eval.getDhe_evaluation())
                )
        );

        return Optional.of(hotelEvaluations);
    }
}