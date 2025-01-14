package org.example.emissionCalculator.service;

import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.model.GeoCodingResponse;

import java.util.List;

import static org.example.emissionCalculator.client.HttpClientService.APIKEY;

public class CityService {

    private HttpClientService httpClientService;

    public CityService(HttpClientService httpClientService){
        this.httpClientService = httpClientService;
    }

    public List<Double> getCityCoordinates(String cityName){
        String geoCodeUrl = "https://api.openrouteservice.org/geocode/search";
        String urlString = String.format("%s?api_key=%s&text=%s&layers=locality",geoCodeUrl,APIKEY,cityName);
        GeoCodingResponse geoCodingResponse = httpClientService.sendGetRequest(urlString, GeoCodingResponse.class);
        return geoCodingResponse.getFeatures().get(0).getGeometry().getCoordinates();
    }
}
