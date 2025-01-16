package com.dev.emissionCalculator.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static com.dev.emissionCalculator.util.constant.AppConstant.GEOCODE_BASE_URL;
import static org.junit.jupiter.api.Assertions.*;

class ApiClientTest {

    private ApiClient apiClient;
    private HttpClient httpClient;
    private HttpResponse<String> httpResponse;


    @BeforeEach
    void setUp(){
        httpClient = Mockito.mock(HttpClient.class);
        httpResponse = Mockito.mock(HttpResponse.class);
        apiClient = new ApiClient();
    }

//    @Test
//    void sendGetRequest_Success(){
//        String url = GEOCODE_BASE_URL;
//        String responseBody = "";
//
//
//    }
}