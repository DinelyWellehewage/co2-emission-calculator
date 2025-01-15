package com.dev.emissionCalculator.service;


import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.model.response.Feature;
import com.dev.emissionCalculator.model.response.GeoCodingResponse;
import com.dev.emissionCalculator.model.response.Geometry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CityServiceTest {

    private ApiClient apiClient;
    private CityService cityService;

    @BeforeEach
    void setUp(){
        apiClient = Mockito.mock(ApiClient.class);
        cityService = new CityService(apiClient);
    }

    @Test
    void getCityCoordinatesTest_Success(){
        String cityName = "Berlin";
        GeoCodingResponse geoCodingResponse = new GeoCodingResponse();
        Geometry geometry = new Geometry();
        List<Double> list = Arrays.asList(13.407032, 52.524932);
        geometry.setCoordinates(list);
        Feature feature = new Feature();
        feature.setGeometry(geometry);
        List<Feature> features = new ArrayList<>();
        features.add(feature);
        geoCodingResponse.setFeatures(features);

        when(apiClient.sendGetRequest(anyString(),eq(GeoCodingResponse.class))).thenReturn(geoCodingResponse);

        List<List<Double>> coordinates = cityService.getCityCoordinates(cityName);
        Assertions.assertEquals(list,coordinates.get(0));
    }

    @Test
    void getCityCoordinatesTest_Failure(){
        String cityName = "Unknown";
        GeoCodingResponse geoCodingResponse = new GeoCodingResponse();
        geoCodingResponse.setFeatures(Collections.emptyList());

        when(apiClient.sendGetRequest(anyString(),eq(GeoCodingResponse.class))).thenReturn(geoCodingResponse);

        List<List<Double>> coordinates = cityService.getCityCoordinates(cityName);
        Assertions.assertTrue(coordinates.isEmpty());

    }

}