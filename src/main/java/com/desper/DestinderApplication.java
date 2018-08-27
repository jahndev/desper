package com.desper;

import com.desper.di.Modules;
import com.desper.resources.HotelResource;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class DestinderApplication extends Application<DestinderConfiguration> {

    private HibernateBundle<DestinderConfiguration> hibernateBundle;

    public static void main(String[] args) throws Exception {
        new DestinderApplication().run(args);
    }

    @Override
    public String getName() {
        return "Destinder";
    }

    @Override
    public void initialize(Bootstrap<DestinderConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        this.hibernateBundle = new ScanningHibernateBundle<DestinderConfiguration>("com.desper.core") {
            @Override
            public DataSourceFactory getDataSourceFactory(DestinderConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };

        bootstrap.addBundle(this.hibernateBundle);

        configObjectMapper(bootstrap.getObjectMapper());
    }

    @Override
    public void run(DestinderConfiguration configuration, Environment environment) {

        // HTTP Client
       /* final JerseyClientConfiguration jerseyClientConf = new JerseyClientConfiguration();
        jerseyClientConf.setGzipEnabled(Boolean.FALSE);
        jerseyClientConf.setGzipEnabledForRequests(Boolean.FALSE);

        final Client client = new JerseyClientBuilder(environment).using(jerseyClientConf).build(getName());
*/

        // dependency injection
        Modules modules = new Modules(hibernateBundle.getSessionFactory());
        Injector injector = Guice.createInjector(modules);

        // resources
        environment.jersey().register(injector.getInstance(HotelResource.class));

    }

    private static void configObjectMapper(ObjectMapper mapper) {
        mapper.setSerializationInclusion(NON_NULL);
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
    }
}
