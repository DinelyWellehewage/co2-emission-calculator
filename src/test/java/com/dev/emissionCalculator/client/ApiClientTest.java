package com.dev.emissionCalculator.client;

import com.dev.emissionCalculator.model.response.GeoCodingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.dev.emissionCalculator.client.ApiClient.APIKEY;
import static com.dev.emissionCalculator.util.constant.AppConstant.GEOCODE_BASE_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ApiClientTest {

    private ApiClient apiClient;
    @Mock
    private HttpClient httpClient;
    private HttpResponse<String> httpResponse;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        httpResponse = Mockito.mock(HttpResponse.class);
        apiClient = new ApiClient(httpClient);
    }

    @Test
    void sendGetRequest_Success() throws IOException, InterruptedException {
        String url = "https://api.openrouteservice.org/geocode/search";
        String responseBody = geoCodingResponse();
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(responseBody);
        when(httpClient.send(any(HttpRequest.class),any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        GeoCodingResponse response = apiClient.sendGetRequest(url, GeoCodingResponse.class);

        assertEquals(10.007046,response.getFeatures().get(0).getGeometry().getCoordinates().get(0));

    }

    private String geoCodingResponse(){
        String geoCodeResponse = "{\n" +
                "    \"features\": [\n" +
                "        {\n" +
                "            \"geometry\": {\n" +
                "                \"coordinates\": [\n" +
                "                    10.007046,\n" +
                "                    53.576158\n" +
                "                ]\n" +
                "            },\n" +
                "            \"properties\": {\n" +
                "                \"country\": \"Germany\",\n" +
                "                \"region\": \"Hamburg\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "    \n" +
                "}";
        return geoCodeResponse;
    }
}