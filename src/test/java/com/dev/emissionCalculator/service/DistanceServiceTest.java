package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.model.response.MatrixResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class DistanceServiceTest {

    private DistanceService distanceService;
    private ApiClient apiClient;

    @BeforeEach
    void setUp(){
        apiClient = Mockito.mock(ApiClient.class);
        distanceService = new DistanceService(apiClient);
    }

    @Test
    void getDistanceMatrix_Success(){
        List<List<Double>> coordinates = List.of(List.of(10.007046,53.576158),List.of(13.407032,52.524932));

        double expectedDistance = 585662.25;

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setDistances(List.of(
                List.of(0.0,expectedDistance),
                List.of(expectedDistance,0.0)
        ));

        when(apiClient.sendPostRequest(anyString(),anyString(),anyString(),eq(MatrixResponse.class)))
                .thenReturn(matrixResponse);
        double distance = distanceService.getDistanceMatrix(coordinates);
        assertEquals(expectedDistance,distance);
    }

}