package com.desper.dao;

import com.desper.core.Hotel;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class HotelDAO extends EntityDao<Hotel>{

    @Inject
    public HotelDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Hotel> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Hotel create(Hotel hotel) {
        return persist(hotel);
    }

    public Optional<List<Hotel>> findHotelsByCity(Long city_id) {

        return Optional.ofNullable(list(namedQuery("com.desper.core.Hotel.findHotelsByCity").setParameter("city_id",city_id)));
    }
}
