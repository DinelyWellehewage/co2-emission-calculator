package com.dev.emissioncalculator.service;

import com.dev.emissioncalculator.util.exception.InvalidInputException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dev.emissioncalculator.client.ApiClient;
import com.dev.emissioncalculator.model.response.MatrixResponse;
import com.dev.emissioncalculator.model.request.MatrixRequest;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev.emissioncalculator.util.constant.AppConstant.*;

/**
 * DistanceService class provides functionality to retrieve distance between two geographical coordinates.
 * It uses Matrix API to process the request.
 * {@link <a href="https://openrouteservice.org/dev/#/api-docs/v2/matrix/">Matrix API</a>}
 */

public class DistanceService {

    private static final Logger logger = LoggerFactory.getLogger(DistanceService.class);

    private ApiClient apiClient;

    public DistanceService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Calculates the distance between two geographic coordinates using MATRIX API.
     *
     * @param coordinates a list of geographic coordinates
     * @return the distance in meters from source and destination coordinates
     * @throws InvalidInputException if no valid distances are found
     * @throws ApiClientException    if an error occurs during API request or response processing
     * @see <a href="https://giscience.github.io/openrouteservice/api-reference/endpoints/matrix/">MATRIX API Documentation</a>
     * The MATRIX API allows to specify source and destination coordinates to calculate the distance between them.
     * This method returns the calculated distance from the start city to end city.
     */

    public double getDistanceMatrix(List<List<Double>> coordinates) {
        logger.info("Calculating distance matrix for coordinates: {}", coordinates);
        String urlString = MATRIX_BASE_URL + PROFILE;
        try {
            String payload = buildRequestPayload(coordinates);
            logger.debug("Constructed Matrix API request payload: {}", payload);

            MatrixResponse matrixResponse = apiClient.sendPostRequest(urlString, payload, ApiClient.APIKEY, MatrixResponse.class);

            List<List<Double>> distanceMatrix = matrixResponse.getDistances();
            List<List<Double>> filteredDistances = filterDistances(distanceMatrix);
            logger.debug("Filtered distance matrix: {}", filteredDistances);

            if (!filteredDistances.isEmpty() && !filteredDistances.get(0).isEmpty()) {
                Double distance = filteredDistances.get(0).get(0);
                logger.debug("Calculated distance: {} meters", distance);
                return distance;
            } else {
                logger.warn("No valid distances found for coordinates: {}", coordinates);
                throw new InvalidInputException("No valid distances found: ");
            }
        } catch (InvalidInputException e) {
            logger.error("Invalid input exception while calculating distance matrix: {}", e.getMessage());
            throw new InvalidInputException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error occurred while fetching distance matrix for coordinates: {}, Error: {}", coordinates, e.getMessage());
            throw new ApiClientException("Error occurred while fetching distance matrix: ", e);
        }
    }

    /**
     * Filter distance matrix to remove null values.
     *
     * @param distanceMatrix the distance matrix retrieved from API
     * @return a filtered distance matrix containing non-null values
     */

    private List<List<Double>> filterDistances(List<List<Double>> distanceMatrix) {
        logger.debug("Filtering distance matrix to remove null values: {}", distanceMatrix);
        return distanceMatrix.stream()
                .map(list -> list.stream()
                        .filter(value -> value != null)
                        .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Builds the JSON payload for the matrix API request.
     *
     * @param coordinates a list of geographical coordinates
     * @return a JSON string to represent request payload
     * @throws ApiClientException if an error occurs during JSON serialization
     */
    private String buildRequestPayload(List<List<Double>> coordinates) {
        logger.debug("Building request payload for coordinates: {}", coordinates);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MatrixRequest matrixRequest = new MatrixRequest();
            matrixRequest.setLocations(coordinates);
            matrixRequest.setMetrics(List.of(METRICS));
            matrixRequest.setSources(List.of("0"));
            matrixRequest.setDestinations(List.of("1"));
            String payload = objectMapper.writeValueAsString(matrixRequest);
            logger.debug("Constructed JSON payload: {}", payload);
            return payload;
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while serializing request payload for coordinates: {}, Error: {}", coordinates, e.getMessage());
            throw new ApiClientException("Error occurred while fetching distance matrix: ", e);
        }
    }
}
