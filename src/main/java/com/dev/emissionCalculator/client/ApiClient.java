package com.dev.emissionCalculator.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import com.dev.emissionCalculator.util.exception.ApiClientException;

public class ApiClient {

    public static final String APIKEY = Optional.ofNullable(Dotenv.load().get("ORS_TOKEN"))
            .orElseThrow(()-> new IllegalArgumentException("API key not found. Please set ORS_TOKEN variable"));

    private final HttpClient httpClient;
    ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    public <T> T sendGetRequest(String urlString,Class<T> responseType){
        try {
            URI uri = URI.create(urlString);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
            return handleResponse(response,responseType);
        } catch (Exception e) {
            throw new ApiClientException("Error sending GET request: "+e.getMessage(),e);
        }
    }
    public <T> T sendPostRequest(String urlString,String body,String apiKey,Class<T> responseType){
        try {
            URI uri = URI.create(urlString);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Authorization",apiKey)
                    .header("Content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
            return handleResponse(response,responseType);
        } catch (Exception e) {
            throw new ApiClientException("Error sending POST request: " +e.getMessage(),e);
        }
    }

    private <T> T handleResponse(HttpResponse<String> response,Class<T> responseType){

        if (response.statusCode()==200){
            try {
                return objectMapper.readValue(response.body(),responseType);
            } catch (JsonProcessingException e) {
                throw new ApiClientException("Error processing JSON response",e);
            }
        } else {
            throw new ApiClientException("HTTP request failed with status code: "+response.statusCode() + ",Body: "+response.body());
        }
    }
}
