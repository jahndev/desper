package com.desper.dto;

import com.desper.dto.view.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPriceDetailDto implements java.io.Serializable {

    @JsonView(View.Public.class)
    private String currency; //representa el nombre del hotel

    @JsonView(View.Public.class)
    private Long subtotal; //representa las estrellas del hotel

    @JsonView(View.Public.class)
    private Long total; //representa las estrellas del hotel

    public HotelPriceDetailDto(String currency, Long total, Long subTotal) {
        this.currency = currency;
        this.subtotal = subTotal;
        this.total = total;
    }

    public HotelPriceDetailDto() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subTotal) {
        this.subtotal = subTotal;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
