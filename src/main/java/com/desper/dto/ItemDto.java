package com.desper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.ws.rs.Produces;
import java.util.List;

@Produces("application/json")
@JsonRootName("item")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {

    private List<HotelDto> items;

    public ItemDto() { }

    public List<HotelDto> getItems() {
        return items;
    }

    public void setItems(List<HotelDto> items) {
        this.items = items;
    }
}
