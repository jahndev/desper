package com.desper.util.despegar;

import com.desper.dto.HotelDto;
import com.desper.dto.ItemDto;
import com.desper.dto.ItemPriceDto;
import com.desper.util.ConfigReader;
import io.dropwizard.jersey.gzip.GZipDecoder;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DespegarClient {

    private static final Logger LOGGER = Logger.getLogger(DespegarClient.class);
    ConfigReader config;
    private volatile Map<Long,String> countryCodeByCityIdMap = new ConcurrentHashMap<>();

    public DespegarClient(ConfigReader configReader) {
        config = configReader;
    }

    public List<HotelDto> getBatchHotels() {
        ClientConfig clientConfig = new ClientConfig();

        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget myResource = client.target(config.getApiDespegarUrl())
                .path("hotels")
                .queryParam("offset", config.getOffset().toString())
                .queryParam("limit",config.getChunk().toString());


        myResource.register(new GZipDecoder());

        Response response = myResource.request(MediaType.APPLICATION_JSON)
                .header("content-type", "application/json")
                .header("charset","UTF-8")
                .header("X-ApiKey", config.getApiKey())
                .header("Accept-Encoding", "gzip")
                .get(new GenericType<Response>(){});

        if (response.getStatus() != 200) {
            LOGGER.error("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getStatusInfo().toString();
            LOGGER.error("Error: "+error);
            return Collections.emptyList();
        }

        ItemDto hotelList = response.readEntity(ItemDto.class);

        return hotelList == null ? Collections.emptyList() : hotelList.getItems();
    }


    public List<HotelDto> loadPriceByHotelId(List<HotelDto> hotelDtos) {
        ClientConfig clientConfig = new ClientConfig();

        Client client = ClientBuilder.newClient(clientConfig);

        StringBuilder hotelListId = new StringBuilder();
        hotelDtos.forEach( h -> hotelListId.append(h.getId()+
                (hotelDtos.indexOf(h) < hotelDtos.size()-1?",":"")));

        String countryCode = getCountryCodeByCity(
                hotelDtos.get(0).getLocation().getCityDto().getId());

        WebTarget myResource = client.target(config.getApiDespegarUrl())
                .path("hotels/prices")
                .queryParam("hotels", hotelListId)
                .queryParam("country", countryCode)
                .queryParam("distribution=2");

        myResource.register(new GZipDecoder());

        Response response = myResource.request(MediaType.APPLICATION_JSON)
                .header("content-type", "application/json")
                .header("charset","UTF-8")
                .header("X-ApiKey", config.getApiKey())
                .header("Accept-Encoding", "gzip")
                .get(new GenericType<Response>(){});

        if (response.getStatus() != 200) {
            LOGGER.error("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getStatusInfo().toString();
            LOGGER.error("Error: "+error);
            return Collections.emptyList();
        }

        ItemPriceDto hotelList = response.readEntity(ItemPriceDto.class);

        hotelDtos.forEach( h -> h.setPrice(hotelList.getItems().get(hotelDtos.indexOf(h)).getPrice_detail()));

        return hotelList == null ? Collections.emptyList() : hotelDtos;
    }

    public JSONObject getCountryById(Long countryId) {
        //https://api.despegar.com/v3/countries/20010
        JSONObject json = null;
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget myResource = client.target(config.getApiDespegarUrl())
                .path("countries").path(String.valueOf(countryId));

        myResource.register(new GZipDecoder());

        Response response = myResource.request(MediaType.APPLICATION_JSON)
                .header("content-type", "application/json")
                .header("charset","UTF-8")
                .header("X-ApiKey", config.getApiKey())
                .header("Accept-Encoding", "gzip")
                .get(new GenericType<Response>(){});

        if (response.getStatus() != 200) {
            LOGGER.error("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getStatusInfo().toString();
            LOGGER.error("Error: "+error);
            return null;
        }

        JSONParser parser = new JSONParser();
        String responseBody = response.readEntity(String.class);

        try {
            json = (JSONObject)parser.parse(responseBody);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        return json;
    }

    public JSONObject getCityById(Long cityId){
        //https://api.despegar.com/v3/cities/901
        JSONObject json = null;

        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget myResource = client.target(config.getApiDespegarUrl())
                .path("cities").path(String.valueOf(cityId));

        myResource.register(new GZipDecoder());

        Response response = myResource.request(MediaType.APPLICATION_JSON)
                .header("content-type", "application/json")
                .header("charset","UTF-8")
                .header("X-ApiKey", config.getApiKey())
                .header("Accept-Encoding", "gzip")
                .get(new GenericType<Response>(){});

        if (response.getStatus() != 200) {
            LOGGER.error("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getStatusInfo().toString();
            LOGGER.error("Error: "+error);
            return null;
        }

        JSONParser parser = new JSONParser();
        String responseBody = response.readEntity(String.class);

        try {
            json = (JSONObject)parser.parse(responseBody);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        return json;
    }

    public String getCountryCodeByCity(Long cityId){
        String countryCode = null;

        if(countryCodeByCityIdMap.size() > 0 && countryCodeByCityIdMap.containsKey(cityId)){
            countryCode = countryCodeByCityIdMap.get(cityId);
        }else{
            JSONObject jsonCity = getCityById(cityId);

            Long countryId = Long.valueOf(jsonCity.get("country_id").toString());

            countryCode = getCountryById(countryId).get("code").toString();

            countryCodeByCityIdMap.put(cityId,countryCode);
        }
        return countryCode;
    }
}
