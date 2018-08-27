package com.desper.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("city")
public class CityDto{

    private Long id;

    public CityDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
