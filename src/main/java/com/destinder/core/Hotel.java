package com.destinder.core;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;

import javax.persistence.*;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "des_hotels")
public class Hotel implements com.destinder.core.Entity<Long> {

    private Long id; //representa el hotel id
    private String name; //representa el nombre del hotel
    private Long stars; //representa las estrellas del hotel
    private String main_picture;
    private Long cit_id;

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
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    public Long getCit_id() {
        return cit_id;
    }

    public void setCit_id(Long cit_id) {
        this.cit_id = cit_id;
    }
}
