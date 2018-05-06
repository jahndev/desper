package com.destinder.di;

import com.destinder.service.HotelService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import javax.ws.rs.client.Client;

public class Modules extends AbstractModule {

    private final SessionFactory sessionFactory;
    private final Client client;


    public Modules(SessionFactory sessionFactory, Client client) {
        this.sessionFactory = sessionFactory;
        this.client = client;
    }

    protected void configure() {
        // instances
        bind(SessionFactory.class).toInstance(this.sessionFactory);
        bind(Client.class).toInstance(this.client);

        //sevices
        bind(HotelService.class).in(Singleton.class);
    }
}
