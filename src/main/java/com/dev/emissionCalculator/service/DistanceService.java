package com.dev.emissionCalculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.model.response.MatrixResponse;
import com.dev.emissionCalculator.model.request.MatrixRequest;
import com.dev.emissionCalculator.util.exception.ApiClientException;

import java.util.List;

import static com.dev.emissionCalculator.util.constant.AppConstant.*;

public class DistanceService {

    private ApiClient apiClient;

    public DistanceService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * calculates the distance between two geographic coordinates using MATRIX API
     *
     *      This API retrieves the distance matrix. The distance between first and
     *      second coordinates in the provided list is extracted and returned.
     *      The Matrix API returns a distance matrix where the elements at [i][j]
     *      represents the distance from the i-th location the  j-th location.
     *      <a href="https://giscience.github.io/openrouteservice/api-reference/endpoints/matrix/">MATRIX API</a>
     *
     *      Since the input list of coordinates contains only two locations (start and end),
     *      the matrix will be a 2x2 grid. The value at [0][1] corresponds to the distance
     *      from the first location (start) to the second location(end).
     *
     * @param coordinates a list of geographic coordinates
     * @return the distance in meters between the first and second coordinates
     *
     * */

    public double getDistanceMatrix(List<List<Double>> coordinates) {
        String urlString = MATRIX_BASE_URL + PROFILE;
        try {
            String payload = buildRequestPayload(coordinates);
            MatrixResponse matrixResponse = apiClient.sendPostRequest(urlString, payload, ApiClient.APIKEY, MatrixResponse.class);
            return matrixResponse.getDistances().get(0).get(1);
        } catch (Exception e) {
            throw new ApiClientException("Error occurred while fetching distance matrix", e);
        }
    }

    private String buildRequestPayload(List<List<Double>> coordinates){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MatrixRequest matrixRequest = new MatrixRequest();
            matrixRequest.setLocations(coordinates);
            matrixRequest.setMetrics(List.of(METRICS));
            return objectMapper.writeValueAsString(matrixRequest);
        } catch (JsonProcessingException e) {
            throw new ApiClientException("Error occurred while fetching distance matrix", e);
        }

    }
}
