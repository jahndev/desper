package com.destinder.dao;

import com.destinder.core.Hotel;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class HotelDao extends EntityDao<Hotel>{

    @Inject
    public HotelDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


}
