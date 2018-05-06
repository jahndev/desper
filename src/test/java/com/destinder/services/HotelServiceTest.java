package com.destinder.services;

import com.destinder.service.HotelService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HotelServiceTest {

    private HotelService hotelService;

    @Before
    public void setUp(){
        hotelService = new HotelService();
    }

    @Test
    public void loadHotels(){
        assertThat(hotelService.getHotels().isEmpty(),is(false));
    }
}
