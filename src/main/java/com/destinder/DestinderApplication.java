package com.destinder;

import com.destinder.config.DestinderConfiguration;
import com.destinder.di.Modules;
import com.destinder.resources.HotelResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.FilterHolder;

import javax.servlet.*;
import javax.ws.rs.client.Client;

import java.io.IOException;
import java.util.EnumSet;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static javax.servlet.DispatcherType.REQUEST;

public class DestinderApplication extends Application<DestinderConfiguration> {

    public void run(DestinderConfiguration destinderConfiguration, Environment environment) throws Exception {

        // HTTP Client
        final JerseyClientConfiguration jerseyClientConf = new JerseyClientConfiguration();
        jerseyClientConf.setGzipEnabled(Boolean.FALSE);
        jerseyClientConf.setGzipEnabledForRequests(Boolean.FALSE);

        final Client client = new JerseyClientBuilder(environment).using(jerseyClientConf).build(getName());

        // dependency injection
        Modules modules = new Modules(this.hibernate.getSessionFactory(), client);
        Injector injector = Guice.createInjector(modules);

        // resources
        environment.jersey().register(injector.getInstance(HotelResource.class));
        // filters
        environment.getAdminContext().addFilter(new FilterHolder(newHealthFilter()), "/health", EnumSet.of(REQUEST));
    }

    @Override
    public String getName() {
        return "destinder-service";
    }

    public static void main(String[] args) throws Exception {
        new DestinderApplication().run(args);
    }

    private HibernateBundle<DestinderConfiguration> hibernate;

    @Override
    public void initialize(Bootstrap<DestinderConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(newConfigurationSourceProvider(bootstrap));

        this.hibernate = newHibernateBundle();
        bootstrap.addBundle(this.hibernate);

        bootstrap.addBundle(newJava8Bundle());

        configObjectMapper(bootstrap.getObjectMapper());

    }

    private static SubstitutingSourceProvider newConfigurationSourceProvider(Bootstrap<DestinderConfiguration> bootstrap) {
        return new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false));
    }

    private static HibernateBundle<DestinderConfiguration> newHibernateBundle() {
        return new ScanningHibernateBundle<DestinderConfiguration>("com.destinder.core") {

            public DataSourceFactory getDataSourceFactory(DestinderConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    private static Java8Bundle newJava8Bundle() {
        return new Java8Bundle();
    }

    private static void configObjectMapper(ObjectMapper mapper) {
        mapper.setSerializationInclusion(NON_NULL);
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
    }

    private static Filter newHealthFilter() {
        return new Filter() {

            public void destroy() {
                // NOOP
            }

            public void init(FilterConfig filterConfig) throws ServletException {
                // NOOP
            }

            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                String location = "/healthcheck";
                request.getRequestDispatcher(location).forward(request, response);
            }
        };
    }
}
