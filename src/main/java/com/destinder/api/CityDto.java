package com.destinder.api;

public class CityDto implements Identifiable{
    private Long id;

    public CityDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
