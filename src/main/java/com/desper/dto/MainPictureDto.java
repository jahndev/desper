package com.desper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainPictureDto {

    private String url;
    private String id;
    private Long width;
    private Long height;
    private Long order;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public PictureCategoryDto getPicture_category() {
        return picture_category;
    }

    public void setPicture_category(PictureCategoryDto picture_category) {
        this.picture_category = picture_category;
    }

    private PictureCategoryDto picture_category;
}
