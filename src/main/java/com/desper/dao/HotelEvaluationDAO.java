package com.desper.dao;

import com.desper.core.Hotel;
import com.desper.core.HotelEvaluation;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class HotelEvaluationDAO extends EntityDao<HotelEvaluation>{

    @Inject
    public HotelEvaluationDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<HotelEvaluation> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public HotelEvaluation create(HotelEvaluation hotelEvaluation) {
        return persist(hotelEvaluation);
    }

    public void delete(HotelEvaluation hotelEvaluation){
        currentSession().delete(hotelEvaluation);
    }

    public Optional<HotelEvaluation> findByHotelAndUser(Long hotelId, String userName) {
        return Optional.ofNullable(uniqueResult(
            namedQuery("com.desper.core.HotelEvaluation.findByHotelAndUser")
            .setParameter("hotelId",hotelId)
            .setParameter("userName",userName)));
    }

    public Optional<List<HotelEvaluation>> findByHotel(Long hotelId) {
            return Optional.ofNullable(list(
                    namedQuery("com.desper.core.HotelEvaluation.findByHotel")
                            .setParameter("hotelId",hotelId)));
    }

    public Optional<List<HotelEvaluation>> findByUser(String userName) {
        return Optional.ofNullable(list(
                namedQuery("com.desper.core.HotelEvaluation.findByUser")
                        .setParameter("userName",userName)));
    }
}
