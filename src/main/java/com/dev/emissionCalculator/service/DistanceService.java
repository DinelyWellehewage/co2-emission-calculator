package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.util.exception.InvalidInputException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.model.response.MatrixResponse;
import com.dev.emissionCalculator.model.request.MatrixRequest;
import com.dev.emissionCalculator.util.exception.ApiClientException;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev.emissionCalculator.util.constant.AppConstant.*;

public class DistanceService {

    private ApiClient apiClient;

    public DistanceService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * calculates the distance between two geographic coordinates using MATRIX API
     *
     *
     * @see <a href="https://giscience.github.io/openrouteservice/api-reference/endpoints/matrix/">MATRIX API Documentation</a>
     * The MATRIX API allows to specify source and destination coordinates to calculate
     * the distance between them.
     * This method returns the calculated distance from the start city to end city
     *
     * @param coordinates a list of geographic coordinates
     * @return the distance in meters from source and destination coordinates
     */

    public double getDistanceMatrix(List<List<Double>> coordinates) {
        String urlString = MATRIX_BASE_URL + PROFILE;
        try {
            String payload = buildRequestPayload(coordinates);
            MatrixResponse matrixResponse = apiClient.sendPostRequest(urlString, payload, ApiClient.APIKEY, MatrixResponse.class);
            List<List<Double>> distanceMatrix =  matrixResponse.getDistances();
            List<List<Double>> filteredDistances = filterDistances(distanceMatrix);
            if (!filteredDistances.isEmpty() && !filteredDistances.get(0).isEmpty()){
                return filteredDistances.get(0).get(0);
            }else {
                throw new InvalidInputException("No valid distances found");
            }
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage(),e);
        } catch (Exception e) {
            throw new ApiClientException("Error occurred while fetching distance matrix", e);
        }
    }

    private List<List<Double>> filterDistances(List<List<Double>> distanceMatrix) {
        return distanceMatrix.stream()
                .map(list->list.stream()
                        .filter(value->value!=null)
                        .collect(Collectors.toList()))
                .filter(list-> !list.isEmpty())
                .collect(Collectors.toList());
    }

    private String buildRequestPayload(List<List<Double>> coordinates) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MatrixRequest matrixRequest = new MatrixRequest();
            matrixRequest.setLocations(coordinates);
            matrixRequest.setMetrics(List.of(METRICS));
            matrixRequest.setSources(List.of("0"));
            matrixRequest.setDestinations(List.of("1"));
            return objectMapper.writeValueAsString(matrixRequest);
        } catch (JsonProcessingException e) {
            throw new ApiClientException("Error occurred while fetching distance matrix", e);
        }

    }
}
