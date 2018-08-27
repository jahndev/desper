package com.desper.dto;

import com.desper.core.HotelEvaluation;
import com.desper.dto.view.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.ws.rs.Produces;

@Produces("application/json")
public class HotelEvaluationDto {

    @JsonView(View.Evaluation.class)
    private String userName;

    @JsonView(View.UserEvaluations.class)
    private Long hotelId;

    private int evaluation;

    public int getEvaluation() {
        return evaluation;
    }

    @JsonView({View.Evaluation.class, View.UserEvaluations.class})
    @JsonProperty("evaluation")
    public String getLabelEvaluation(){
        return evaluation == HotelEvaluation.LIKE ? "like" : "dislike";
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public HotelEvaluationDto(Long hotelId, String userName,int evaluation) {
        this.setUserName(userName);
        this.setHotelId(hotelId);
        this.setEvaluation(evaluation);
    }

    public HotelEvaluationDto() {
    }

    public HotelEvaluationDto(String userName) {
        this.userName = userName;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
