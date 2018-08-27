package com.desper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {

    private CityDto city;

    public LocationDto() {}

    public CityDto getCityDto() {
        return city;
    }

    @JsonProperty("city")
    public void setCityDto(CityDto city) {
        this.city = city;
    }

}
