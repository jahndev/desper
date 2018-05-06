package com.destinder.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.destinder.api.CityDto;
import com.destinder.api.HotelDto;
import com.destinder.api.LocationDto;
import com.destinder.controller.HotelController;
import com.sun.xml.internal.ws.util.StringUtils;
import io.dropwizard.hibernate.UnitOfWork;
import liquibase.util.NumberUtils;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/hotels")
@Produces(APPLICATION_JSON)
public class HotelResource {

    private final HotelController hotelController;

    @Inject
    public HotelResource(HotelController hotelController) {
        this.hotelController = hotelController;
    }

    @GET
    @Timed
    @ExceptionMetered
    @UnitOfWork(readOnly = true, transactional = false)
    @Path("/prices")
    public HotelDto getPricesOfHotelsByCityId(@NotNull @QueryParam("city_id") Long cityId) {

        LocationDto locationDto = new LocationDto();
        CityDto cityDto = new CityDto();
        cityDto.setId(cityId);
        locationDto.setCityDto(cityDto);
        HotelDto hotelDto = new HotelDto(null,null,locationDto,null);

        return (HotelDto) hotelController.findHotelsByCityIds(hotelDto);
    }
}
