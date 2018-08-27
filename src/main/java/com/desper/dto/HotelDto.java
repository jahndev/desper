package com.desper.dto;

import com.desper.dto.view.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDto implements java.io.Serializable, Identifiable {

    @JsonView(View.Public.class)
    private Long id; //representa el hotel id

    @JsonView(View.Public.class)
    private String name; //representa el nombre del hotel

    @JsonView(View.Public.class)
    private Long stars; //representa las estrellas del hotel

    private MainPictureDto main_picture;

    @JsonView(View.Public.class)
    @JsonProperty("main_picture")
    public String getMainPictureUrl(){
        return main_picture != null ? main_picture.getUrl():"";
    }

    @JsonProperty("location")
    public LocationDto location;

    @JsonView(View.Public.class)
    @JsonProperty("price_detail")
    private HotelPriceDetailDto price;

    public HotelDto(Long id, String name, Long stars, MainPictureDto main_picture,LocationDto location) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.main_picture = main_picture;
        this.location = location;
    }

    public HotelDto() {
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public HotelDto(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    public MainPictureDto getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(MainPictureDto main_picture) {
        this.main_picture = main_picture;
    }

    public HotelPriceDetailDto getPrice() {
        return price;
    }

    public void setPrice(HotelPriceDetailDto price) {
        this.price = price;
    }
}
