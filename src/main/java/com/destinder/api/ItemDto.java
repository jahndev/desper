package com.destinder.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Produces("application/json")
@XmlRootElement
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
