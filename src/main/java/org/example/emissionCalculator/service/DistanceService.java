package org.example.emissionCalculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.emissionCalculator.client.ApiClient;
import org.example.emissionCalculator.model.MatrixResponse;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.emissionCalculator.util.constant.AppConstant.MATRIX_BASE_URL;
import static org.example.emissionCalculator.util.constant.AppConstant.PROFILE;

public class DistanceService {

    private static final Logger LOGGER = Logger.getLogger(DistanceService.class.getName());
    private ApiClient apiClient;

    public DistanceService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public double getDistanceMatrix(List<List<Double>> coordinates) {
        String urlString = MATRIX_BASE_URL + PROFILE;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String payload = objectMapper.writeValueAsString(Map.of("locations", coordinates, "metrics", List.of("distance")));
            MatrixResponse matrixResponse = apiClient.sendPostRequest(urlString, payload, ApiClient.APIKEY, MatrixResponse.class);
            /**
             * calculates the distance between two geographic coordinates using MATRIX API
             *
             * This API retrieves the distance matrix. The distance between first and second coordinates in the provided list is extracted and returned.
             * The Matrix API returns a distance matrix where the elements at [i][j]
             * represents the distance from the i-th location the  j-th location.
             *
             * Since the input list of coordinates contains only two locations (start and end),
             * the matrix will be a 2x2 grid. The value at [0][1] corresponds to the distance
             * from the first location (start) to the second location(end).
             * */
            Double distance = matrixResponse.getDistances().get(0).get(1);
            return distance;

        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Failed to serialize payload for MATRIX API", e);
            throw new RuntimeException("Failed to serialize payload for MATRIX API", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching distance matrix", e);
            throw new RuntimeException("Error occurred while fetching distance matrix", e);
        }


    }
}
