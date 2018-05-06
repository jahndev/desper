package com.destinder.util.ApiDesperClient;

import com.destinder.api.HotelDto;
import com.destinder.api.ItemDto;
import com.destinder.util.ConfigReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DespegarClient {

    private static final Logger LOGGER = Logger.getLogger(DespegarClient.class);
    ConfigReader config;

    public DespegarClient(ConfigReader configReader) {
        config = configReader;
    }

    public List<HotelDto> getBatchHotels() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);

        Client client = Client.create(clientConfig);
        client.addFilter(new GZIPContentEncodingFilter(false));

        WebResource webResource = client.resource(config.getApiDespegarUrl())
                .path("hotels")
                .queryParam("offset", config.getOffset().toString())
                .queryParam("limit",config.getChunk().toString());

        WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
                .header("content-type", "application/json;charset=UTF-8")
                .header("X-ApiKey", config.getApiKey())
                .header("Accept-Encoding", "gzip");

        ClientResponse response = builder.get(new GenericType<ClientResponse>(){});

        if (response.getStatus() != 200) {
            LOGGER.error("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getEntity(String.class);
            LOGGER.error("Error: "+error);
            return Collections.emptyList();
        }

        ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonContent = response.getEntity(String.class);
        ItemDto hotelList = null;
        try {
            hotelList = mapper.readValue(jsonContent, ItemDto.class);
        } catch (IOException e) {
            LOGGER.error("Error parsing json information "+e.getMessage(),e);
        }

        return hotelList == null ? Collections.emptyList() : hotelList.getItems();
    }
}
