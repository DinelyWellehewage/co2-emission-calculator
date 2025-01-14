package org.example.emissionCalculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.model.MatrixResponse;

import java.util.List;
import java.util.Map;

public class DistanceService {

    private HttpClientService httpClientService;

    public DistanceService(HttpClientService httpClientService){
        this.httpClientService = httpClientService;
    }

    public double getDistanceMatrix(double[][] coordinates){
        String matrixUrl = "https://api.openrouteservice.org/v2/matrix";
        String profile = "driving-car";
        String urlString = String.format("%s/%s",matrixUrl,profile);
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(Map.of("locations",coordinates,"metrics", List.of("distance")));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MatrixResponse matrixResponse = httpClientService.sendPostRequest(urlString,payload,HttpClientService.APIKEY, MatrixResponse.class);

        Double distance = matrixResponse.getDistances().get(0).get(1);
        return distance;
    }
}
