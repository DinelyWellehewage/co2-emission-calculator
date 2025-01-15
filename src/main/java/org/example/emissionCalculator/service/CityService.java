package org.example.emissionCalculator.service;

import org.example.emissionCalculator.client.ApiClient;
import org.example.emissionCalculator.model.GeoCodingResponse;
import org.example.emissionCalculator.util.exception.ApiClientException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.example.emissionCalculator.client.ApiClient.APIKEY;
import static org.example.emissionCalculator.util.constant.AppConstant.GEOCODE_BASE_URL;
import static org.example.emissionCalculator.util.constant.AppConstant.LAYER_LOCALITY;

public class CityService {

    private static final Logger LOGGER = Logger.getLogger(CityService.class.getName());

    private ApiClient apiClient;

    public CityService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<List<Double>> getCityCoordinates(String cityName) {

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
                            .map(feature -> feature.getGeometry().getCoordinates())
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching coordinates for city: " + cityName);
            throw new ApiClientException("Error fetching coordinates for city: " + e.getMessage());
        }

    }
}
