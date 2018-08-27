package com.desper.resources;

import com.desper.controller.HotelController;
import com.desper.core.Hotel;
import com.desper.core.HotelEvaluation;
import com.desper.dao.HotelDAO;
import com.desper.dao.HotelEvaluationDAO;
import com.desper.dto.HotelDto;
import com.desper.dto.HotelEvaluationDto;
import com.desper.dto.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.AbstractParam;
import io.dropwizard.jersey.params.LongParam;
import org.apache.http.HttpStatus;
import org.eclipse.jetty.util.StringUtil;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelResource {

    private final HotelDAO hotelDAO;
    private final HotelController hotelController;
    private final HotelEvaluationDAO hotelEvaluationDAO;

    @Inject
    public HotelResource(HotelDAO hotelDAO, HotelController hotelController,HotelEvaluationDAO hotelEvaluationDAO) {
        this.hotelDAO = hotelDAO;
        this.hotelController = hotelController;
        this.hotelEvaluationDAO = hotelEvaluationDAO;
    }

    @GET
    @Path("/prices")
    @JsonView(View.Public.class)
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelDto> getHotelsByCity(@QueryParam("city_id") LongParam cityId) {

        return hotelController.findHotelsByCityIds(cityId.get()).orElseThrow(() -> new NotFoundException("No such hotels."));
    }

    private Hotel findSafely(long city_id) {
        return hotelDAO.findById(city_id).orElseThrow(() -> new NotFoundException("No such hotels."));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/likes")
    @UnitOfWork
    public Response like(@NotNull HotelEvaluationDto hotelEvaluationDto){
        hotelEvaluationDto.setEvaluation(HotelEvaluation.LIKE);
        try {
            hotelController.setEvaluation(hotelEvaluationDto);
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Consumes(APPLICATION_JSON)
    @Path("/likes")
    @UnitOfWork
    public Response deleteLike(@NotNull HotelEvaluationDto hotelEvaluationDto){
        hotelEvaluationDto.setEvaluation(HotelEvaluation.LIKE);
        hotelController.deleteEvaluation(hotelEvaluationDto);
        return Response.ok().build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/dislikes")
    @UnitOfWork
    public Response dislike(@NotNull HotelEvaluationDto hotelEvaluationDto){
        hotelEvaluationDto.setEvaluation(HotelEvaluation.DISLIKE);
        try {
            hotelController.setEvaluation(hotelEvaluationDto);
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Consumes(APPLICATION_JSON)
    @UnitOfWork
    @Path("/dislikes")
    public Response deleteDislike(@NotNull HotelEvaluationDto hotelEvaluationDto){
        hotelEvaluationDto.setEvaluation(HotelEvaluation.DISLIKE);
        hotelController.deleteEvaluation(hotelEvaluationDto);
        return Response.ok().build();
    }

    @GET
    @UnitOfWork
    @JsonView(View.Evaluation.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/evaluations/{hotel_id}")
    public List<HotelEvaluationDto> getEvaluationsByHotelId(@PathParam("hotel_id") Long hotel_id) {

        if(hotel_id == 0 ){
            throw new BadRequestException("hotel_id is not a valid hotelId.");
        }

        return hotelController.findEvaluationsByHotel(
            new HotelEvaluationDto(hotel_id,null,0))
                .orElseThrow(() -> new NotFoundException("No such Evaluations by hotelid: "+hotel_id));
    }

    @GET
    @UnitOfWork
    @JsonView(View.UserEvaluations.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public List<HotelEvaluationDto> getEvaluationsByUser(@QueryParam("userName") @NotNull String userName) {

        if(StringUtil.isBlank(userName)){
            throw new BadRequestException("userName cannot be null or empty.");
        }

        return hotelController.findEvaluationsByUser(new HotelEvaluationDto(userName))
                    .orElseThrow(() -> new NotFoundException("No such Evaluations by User: "+userName));
    }
}
