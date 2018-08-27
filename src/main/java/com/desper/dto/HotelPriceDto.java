package com.desper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPriceDto implements java.io.Serializable {

    private String id;

    private Long hotel_id;

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    private HotelPriceDetailDto price_detail;

    public HotelPriceDto(String id,Long hotel_id,HotelPriceDetailDto price_detail) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.price_detail = price_detail;
    }

    public HotelPriceDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HotelPriceDetailDto getPrice_detail() {
        return price_detail;
    }

    public void setPrice_detail(HotelPriceDetailDto price_detail) {
        this.price_detail = price_detail;
    }
}
