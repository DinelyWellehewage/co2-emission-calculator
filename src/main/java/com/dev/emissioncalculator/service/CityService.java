package com.dev.emissioncalculator.service;

import com.dev.emissioncalculator.client.ApiClient;
import com.dev.emissioncalculator.model.response.GeoCodingResponse;
import com.dev.emissioncalculator.model.response.LocationInfo;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dev.emissioncalculator.client.ApiClient.APIKEY;
import static com.dev.emissioncalculator.util.constant.AppConstant.GEOCODE_BASE_URL;
import static com.dev.emissioncalculator.util.constant.AppConstant.LAYER_LOCALITY;


/**
 * CityService class provides functionality for retrieving geographical information for a given city.
 * It uses Geocode API to process the request.
 * {@link <a href="https://openrouteservice.org/dev/#/api-docs/geocode/search/get">Geocode API</a>}
 */

public class CityService {

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    private ApiClient apiClient;

    public CityService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     *Retrieves the geographical information for a given city name.
     *
     * @param cityName the name of the city to fetch coordinates
     * @return a list of objects containing location information
     * @throws ApiClientException if an error occurs during API request or response processing
     */

    public List<LocationInfo> getCityCoordinates(String cityName) {
        logger.info("Fetching coordinates for city: {}",cityName);
        try {
            String urlString = String.format("%s?api_key=%s&text=%s&layers=%s",
                    GEOCODE_BASE_URL,
                    URLEncoder.encode(APIKEY, StandardCharsets.UTF_8),
                    URLEncoder.encode(cityName, StandardCharsets.UTF_8),
                    URLEncoder.encode(LAYER_LOCALITY, StandardCharsets.UTF_8));

            GeoCodingResponse geoCodingResponse = apiClient.sendGetRequest(urlString, GeoCodingResponse.class);

            List<LocationInfo> locationInfos = Optional.ofNullable(geoCodingResponse)
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

            logger.debug("Found {} locations for city: {}",locationInfos.size(),cityName);
            logger.debug("Locations: {}",locationInfos);

            return locationInfos;
        } catch (Exception e) {
            logger.error("Error fetching coordinates for city: {}",cityName,e);
            throw new ApiClientException("Error fetching coordinates for city: " + e.getMessage());
        }
    }
}
