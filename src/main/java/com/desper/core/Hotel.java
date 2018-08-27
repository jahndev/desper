package com.desper.core;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "des_hotels")
@NamedQueries({
        @NamedQuery(
            name = "com.desper.core.Hotel.findHotelsByCity",
                        query = "Select h from Hotel h where h.city_id = :city_id"
                )
        })
public class Hotel implements java.io.Serializable, com.desper.core.Entity<Long> {
    @Id
    @Column(name = "htl_id", nullable = false)
    private Long id; //representa el hotel id

    @Column(name = "htl_name", nullable = false)
    private String name; //representa el nombre del hotel

    @Column(name = "htl_stars", nullable = false)
    private Long stars; //representa las estrellas del hotel

    @Column(name = "htl_main_picture", nullable = false)
    private String main_picture;

    @Column(name = "htl_city_id", nullable = false)
    private Long city_id;

    public Hotel() {
    }

    public Hotel(Long id, String name, Long stars, String main_picture, Long city_id) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.main_picture = main_picture;
        this.city_id = city_id;
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

    public String getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(String main_picture) {
        this.main_picture = main_picture;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return city_id;
    }

    public void setCityId(Long cit_id) {
        this.city_id = cit_id;
    }
}
