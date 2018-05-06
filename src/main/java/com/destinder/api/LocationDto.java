package com.destinder.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {

    private CityDto city;

    public LocationDto() {}

    public CityDto getCityDto() {
        return city;
    }

    public void setCityDto(CityDto city) {
        this.city = city;
    }

}
