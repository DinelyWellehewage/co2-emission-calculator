package com.dev.emissioncalculator.client;

import com.dev.emissioncalculator.model.response.GeoCodingResponse;
import com.dev.emissioncalculator.model.response.MatrixResponse;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ApiClientTest {


    private ApiClient apiClient;
    @Mock
    private HttpClient httpClient;
    private HttpResponse<String> httpResponse;


    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
        httpResponse = Mockito.mock(HttpResponse.class);
        apiClient = new ApiClient();
        var httpClientField = ApiClient.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(apiClient,httpClient);


    }

    @Test
    void sendGetRequestTest_Success() throws IOException, InterruptedException {
        String url = "https://api.openrouteservice.org/geocode/search";
        String responseBody = geoCodingResponse();
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(responseBody);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        GeoCodingResponse response = apiClient.sendGetRequest(url, GeoCodingResponse.class);

        assertEquals(10.007046, response.getFeatures().get(0).getGeometry().getCoordinates().get(0));

    }

    @Test
    void sendGetRequestTest_Failure() throws IOException, InterruptedException {
        String url = "https://api.openrouteservice.org/geocode/search";
        when(httpResponse.statusCode()).thenReturn(404);
        when(httpResponse.body()).thenReturn("Not Found");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        ApiClientException exception = assertThrows(ApiClientException.class, () -> {
            apiClient.sendGetRequest(url, GeoCodingResponse.class);
        });

        assertTrue(exception.getMessage().contains("HTTP request failed with status code: "));
    }
    @Test
    void sendPostRequestTest_Success() throws IOException, InterruptedException {
        String url = "https://api.openrouteservice.org/v2/matrix";
        String requestBody = matrixRequest();
        String responseBody = matrixResponse();
        String apikey = "test-api-key";
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(responseBody);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        MatrixResponse response = apiClient.sendPostRequest(url,requestBody,apikey, MatrixResponse.class);
        assertEquals(287735.81, response.getDistances().get(0).get(0));
    }
    @Test
    void sendPostRequestTest_Failure() throws IOException, InterruptedException {
        String url = "https://api.openrouteservice.org/v2/matrix";
        String requestBody = matrixRequest();
        String apikey = "test-api-key";
        when(httpResponse.statusCode()).thenReturn(500);
        when(httpResponse.body()).thenReturn("Internal server error");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        ApiClientException exception = assertThrows(ApiClientException.class, () -> {
            apiClient.sendPostRequest(url,requestBody,apikey, MatrixResponse.class);
        });

        assertTrue(exception.getMessage().contains("HTTP request failed with status code: "));
    }

    @Test
    void handleResponseInvalidJsonTest() throws IOException, InterruptedException {
        String url = "https://api.openrouteservice.org/geocode/search";

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("{invalid Json}");
        when(httpClient.send(any(HttpRequest.class),any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        ApiClientException exception = assertThrows(ApiClientException.class,()->{
            apiClient.sendGetRequest(url, GeoCodingResponse.class);
        });

        assertTrue(exception.getMessage().contains("Error processing JSON response"));

    }

    private String matrixResponse(){
        String matrixResponse = "{\n" +
                "    \"distances\": [\n" +
                "        [\n" +
                "            287735.81\n" +
                "        ]\n" +
                "    ]\n" +
                "}";
        return matrixResponse;
    }
    private String matrixRequest(){
        String matrixRequest = "{\n" +
                "    \"locations\": [\n" +
                "        [\n" +
                "            10.007046,\n" +
                "            53.576158\n" +
                "        ],\n" +
                "        [\n" +
                "            13.407032,\n" +
                "            52.524932\n" +
                "        ]\n" +
                "    ]\n" +
                "}";
        return matrixRequest;
    }

    private String geoCodingResponse() {
        String geoCodeResponse = "{\n" +
                "    \"features\": [\n" +
                "        {\n" +
                "            \"geometry\": {\n" +
                "                \"coordinates\": [\n" +
                "                    10.007046,\n" +
                "                    53.576158\n" +
                "                ]\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "    \n" +
                "}";
        return geoCodeResponse;
    }
}