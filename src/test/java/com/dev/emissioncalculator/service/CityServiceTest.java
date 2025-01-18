package com.dev.emissioncalculator.service;


import com.dev.emissioncalculator.client.ApiClient;
import com.dev.emissioncalculator.model.response.*;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CityServiceTest {

    private ApiClient apiClient;
    private CityService cityService;

    @BeforeEach
    void setUp() {
        apiClient = Mockito.mock(ApiClient.class);
        cityService = new CityService(apiClient);
    }

    @Test
    void getCityCoordinatesTest_Success() {
        String cityName = "Berlin";
        List<Double> list = Arrays.asList(13.407032, 52.524932);
        String country = "Germany";
        String region = "Berlin";
        GeoCodingResponse geoCodingResponse = new GeoCodingResponse();
        Geometry geometry = new Geometry();
        geometry.setCoordinates(list);

        Properties properties = new Properties();
        properties.setCountry(country);
        properties.setRegion(region);
        Feature feature = new Feature();
        feature.setGeometry(geometry);
        feature.setProperties(properties);
        List<Feature> features = new ArrayList<>();
        features.add(feature);
        geoCodingResponse.setFeatures(features);

        when(apiClient.sendGetRequest(anyString(), eq(GeoCodingResponse.class))).thenReturn(geoCodingResponse);

        List<LocationInfo> result = cityService.getCityCoordinates(cityName);
        Assertions.assertEquals(list, result.get(0).getCoordinates());
    }

    @Test
    void getCityCoordinatesTest_Failure() {
        String cityName = "Unknown";
        GeoCodingResponse geoCodingResponse = new GeoCodingResponse();
        geoCodingResponse.setFeatures(Collections.emptyList());

        when(apiClient.sendGetRequest(anyString(), eq(GeoCodingResponse.class))).thenReturn(geoCodingResponse);

        List<LocationInfo> result = cityService.getCityCoordinates(cityName);
        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    void getCityCoordinatesTest_Exception(){
        String cityName = "Berlin";


        when(apiClient.sendGetRequest(anyString(), eq(GeoCodingResponse.class))).thenThrow(new RuntimeException("API request failed"));

        ApiClientException exception = Assertions.assertThrows(ApiClientException.class,()->{
            cityService.getCityCoordinates(cityName);
        });
        assertTrue(exception.getMessage().contains("Error fetching coordinates for city: "));

    }

}