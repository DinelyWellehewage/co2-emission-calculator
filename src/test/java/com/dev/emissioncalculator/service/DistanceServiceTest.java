package com.dev.emissioncalculator.service;

import com.dev.emissioncalculator.client.ApiClient;
import com.dev.emissioncalculator.model.response.MatrixResponse;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import com.dev.emissioncalculator.util.exception.InvalidInputException;
import org.junit.jupiter.api.Assertions;
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
                List.of(expectedDistance)

        ));

        when(apiClient.sendPostRequest(anyString(),anyString(),anyString(),eq(MatrixResponse.class)))
                .thenReturn(matrixResponse);
        double distance = distanceService.getDistanceMatrix(coordinates);
        assertEquals(expectedDistance,distance);
    }

    @Test
    void getDistanceMatrix_InvalidInputException(){
        List<List<Double>> coordinates = List.of(List.of(10.007046,53.576158),List.of(13.407032,52.524932));

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setDistances(List.of(
                List.of()
        ));

        when(apiClient.sendPostRequest(anyString(),anyString(),anyString(),eq(MatrixResponse.class)))
                .thenReturn(matrixResponse);

        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class,()->{
            distanceService.getDistanceMatrix(coordinates);
        });
        assertTrue(exception.getMessage().contains("No valid distances found"));
    }
    @Test
    void getDistanceMatrix_ApiClientException(){
        List<List<Double>> coordinates = List.of(List.of(10.007046,53.576158),List.of(13.407032,52.524932));

        when(apiClient.sendPostRequest(anyString(),anyString(),anyString(),eq(MatrixResponse.class)))
                .thenThrow(new RuntimeException("API failed"));

        ApiClientException exception = Assertions.assertThrows(ApiClientException.class,()->{
            distanceService.getDistanceMatrix(coordinates);
        });
        assertTrue(exception.getMessage().contains("Error occurred while fetching distance matrix"));
    }



}