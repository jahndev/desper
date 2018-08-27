package com.desper.service;

import com.desper.dto.HotelDto;
import com.desper.util.ConfigReader;
import com.desper.util.despegar.DespegarClient;

import java.util.List;

public class HotelService {

    private DespegarClient despegarApi;
    private ConfigReader configReader;
    private static List<HotelDto> hotels;

    public List<HotelDto> getHotels() {
        return hotels;
    }

    public HotelService() {
        configReader = new ConfigReader();

        setDespegarApi(new DespegarClient(configReader));

        hotels = getDespegarApi().getBatchHotels();
    }

    public DespegarClient getDespegarApi() {
        return despegarApi;
    }

    public void setDespegarApi(DespegarClient despegarApi) {
        this.despegarApi = despegarApi;
    }
}
