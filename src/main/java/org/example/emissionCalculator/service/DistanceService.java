package org.example.emissionCalculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.model.MatrixResponse;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.emissionCalculator.util.constant.AppConstant.MATRIX_BASE_URL;
import static org.example.emissionCalculator.util.constant.AppConstant.PROFILE;

public class DistanceService {

    private static final Logger LOGGER = Logger.getLogger(DistanceService.class.getName());
    private HttpClientService httpClientService;

    public DistanceService(HttpClientService httpClientService){
        this.httpClientService = httpClientService;
    }

    public double getDistanceMatrix(double[][] coordinates){
        String urlString = MATRIX_BASE_URL+PROFILE;
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            String payload = objectMapper.writeValueAsString(Map.of("locations",coordinates,"metrics", List.of("distance")));
            MatrixResponse matrixResponse = httpClientService.sendPostRequest(urlString,payload,HttpClientService.APIKEY, MatrixResponse.class);
            Double distance = matrixResponse.getDistances().get(0).get(1);
            return distance;

        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE,"Failed to serialize payload for MATRIX API",e);
            throw new RuntimeException("Failed to serialize payload for MATRIX API",e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Error occurred while fetching distance matrix",e);
            throw new RuntimeException("Error occurred while fetching distance matrix",e);
        }


    }
}
