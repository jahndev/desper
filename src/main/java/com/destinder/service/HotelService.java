package com.destinder.service;

import com.destinder.api.HotelDto;
import com.destinder.util.ApiDesperClient.DespegarClient;
import com.destinder.util.ConfigReader;

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

        despegarApi = new DespegarClient(configReader);

        hotels = despegarApi.getBatchHotels();
    }
}
