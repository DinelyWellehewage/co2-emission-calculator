package org.example.emissionCalculator.service;

import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.model.GeoCodingResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.emissionCalculator.client.HttpClientService.APIKEY;
import static org.example.emissionCalculator.util.constant.AppConstant.GEOCODE_BASE_URL;
import static org.example.emissionCalculator.util.constant.AppConstant.LAYER_LOCALITY;

public class CityService {

    private static final Logger LOGGER = Logger.getLogger(CityService.class.getName());

    private HttpClientService httpClientService;

    public CityService(HttpClientService httpClientService){
        this.httpClientService = httpClientService;
    }

    public List<Double> getCityCoordinates(String cityName){

        try{
            String urlString = String.format("%s?api_key=%s&text=%s&layers=%s",
                    GEOCODE_BASE_URL,
                    URLEncoder.encode(APIKEY, StandardCharsets.UTF_8),
                    URLEncoder.encode(cityName,StandardCharsets.UTF_8),
                    URLEncoder.encode(LAYER_LOCALITY));
            GeoCodingResponse geoCodingResponse = httpClientService.sendGetRequest(urlString, GeoCodingResponse.class);
            return Optional.ofNullable(geoCodingResponse)
                    .map(GeoCodingResponse::getFeatures)
                    .filter(features -> !features.isEmpty())
                    .map(feature -> feature.get(0))
                    .map(feature -> feature.getGeometry().getCoordinates())
                    .orElseGet(()->{
                        LOGGER.log(Level.WARNING,"No coordinates found for city: {0}",cityName);
                        return Collections.emptyList();
                    });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Error fetching coordinates for city: {0}"+cityName,e);
            return Collections.emptyList();
        }

    }
}
