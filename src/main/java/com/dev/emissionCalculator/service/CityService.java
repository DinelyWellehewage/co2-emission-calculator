package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.model.response.GeoCodingResponse;
import com.dev.emissionCalculator.model.response.LocationInfo;
import com.dev.emissionCalculator.util.exception.ApiClientException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dev.emissionCalculator.client.ApiClient.APIKEY;
import static com.dev.emissionCalculator.util.constant.AppConstant.GEOCODE_BASE_URL;
import static com.dev.emissionCalculator.util.constant.AppConstant.LAYER_LOCALITY;

public class CityService {

    private ApiClient apiClient;

    public CityService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<LocationInfo> getCityCoordinates(String cityName) {

        try {
            String urlString = String.format("%s?api_key=%s&text=%s&layers=%s",
                    GEOCODE_BASE_URL,
                    URLEncoder.encode(APIKEY, StandardCharsets.UTF_8),
                    URLEncoder.encode(cityName, StandardCharsets.UTF_8),
                    URLEncoder.encode(LAYER_LOCALITY, StandardCharsets.UTF_8));
            GeoCodingResponse geoCodingResponse = apiClient.sendGetRequest(urlString, GeoCodingResponse.class);
            return Optional.ofNullable(geoCodingResponse)
                    .map(GeoCodingResponse::getFeatures)
                    .filter(features -> !features.isEmpty())
                    .map(features -> features.stream()
                            .map(feature -> new LocationInfo(
                                    feature.getGeometry().getCoordinates(),
                                    feature.getProperties().getCountry(),
                                    feature.getProperties().getRegion()
                            ))
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            throw new ApiClientException("Error fetching coordinates for city: " + e.getMessage());
        }
    }
}
