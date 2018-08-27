package com.desper.di;

import com.desper.controller.HotelController;
import com.desper.core.HotelEvaluation;
import com.desper.dao.HotelDAO;
import com.desper.dao.HotelEvaluationDAO;
import com.desper.mapper.HotelMapper;
import com.desper.resources.HotelResource;
import com.desper.service.HotelService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;

import javax.ws.rs.client.Client;

public class Modules extends AbstractModule {

    private final SessionFactory sessionFactory;

    public Modules(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected void configure() {
        // instances
        bind(SessionFactory.class).toInstance(this.sessionFactory);

        //sevices
        bind(HotelDAO.class).in(Singleton.class);
        bind(HotelEvaluationDAO.class).in(Singleton.class);
        bind(HotelMapper.class).in(Singleton.class);
        bind(HotelService.class).in(Singleton.class);
        bind(HotelController.class).in(Singleton.class);
        bind(HotelResource.class).in(Singleton.class);
    }
}
