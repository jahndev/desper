package com.destinder.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Objects;

import static java.util.Objects.hash;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDto implements Identifiable{

    private Long id;
    private String name; //=> representa el nombre del hotel
    private LocationDto location; // => representa el id de la ciudad, ver que esta dentro de location.city.id
    private Long stars; // => representa las estrellas del hotel
    private MainPictureDto main_picture; // => representa la foto principal

    public HotelDto() {}

    public HotelDto(Long id, String name,Long starts, LocationDto location, MainPictureDto main_picture){
        this.id = id;
        this.name = name;
        this.stars = starts;
        this.location = location;
        this.main_picture = main_picture;
    }

    @Override
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public LocationDto getLocation() { return location; }

    public void setLocation(LocationDto location){
        this.location = location;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    @JsonIgnore
    public MainPictureDto getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(MainPictureDto main_picture) {
        this.main_picture = main_picture;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HotelDto that = (HotelDto) obj;
        return Objects.equals(this.id, that.id) && Objects.equals(
                this.location.getCityDto().getId(), that.getLocation().getCityDto().getId())
                && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return hash(this.id, this.location.getCityDto().getId(), this.name);
    }
}
