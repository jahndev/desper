package com.desper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.ws.rs.Produces;
import java.util.List;

@Produces("application/json")
@JsonRootName("item")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemPriceDto {

    private List<HotelPriceDto> items;

    public ItemPriceDto() { }

    public List<HotelPriceDto> getItems() {
        return items;
    }

    public void setItems(List<HotelPriceDto> items) {
        this.items = items;
    }
}
