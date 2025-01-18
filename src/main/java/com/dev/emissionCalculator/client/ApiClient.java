package com.dev.emissionCalculator.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dev.emissionCalculator.util.exception.ApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dev.emissionCalculator.util.constant.AppConstant.ORS_TOKEN;

/**
 * ApiClient class is responsible for handling HTTP requests and responses.
 * It supports sending GET and POST requests using HttpClient and processing responses.
 */

public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    //API key used for authentication, retrieved from environment variable.
    public static final String APIKEY = Optional.ofNullable(System.getenv(ORS_TOKEN))
            .orElseThrow(() -> new IllegalArgumentException("API key not found. Please set ORS_TOKEN variable"));

    private final HttpClient httpClient;
    ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Sends an HTTP GET request to the specified URL and process the response.
     *
     * @param urlString    the URL to send the GET request
     * @param responseType the class type of expected response
     * @param <T>          the type of response body
     * @return the deserialized response body
     * @throws ApiClientException if the request fails or the response cannot be processed
     */

    public <T> T sendGetRequest(String urlString, Class<T> responseType) {
        try {
            URI uri = URI.create(urlString);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Received response for GET request Status Code:{}",response.statusCode());

            return handleResponse(response, responseType);
        } catch (Exception e) {
            logger.error("Error sending GET request ",e);
            throw new ApiClientException("Error sending GET request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP POST request to the specified URL and process the response.
     *
     * @param urlString    the URL to send the POST request
     * @param body         the request body to POST request
     * @param apiKey       the API key for authorization
     * @param responseType the class type of expected response
     * @param <T>          the type of response body
     * @return the deserialized response body
     * @throws ApiClientException if the request fails or the response cannot be processed
     */
    public <T> T sendPostRequest(String urlString, String body, String apiKey, Class<T> responseType) {
        try {
            URI uri = URI.create(urlString);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Authorization", apiKey)
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            logger.debug("Request body: {}",body);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Received response for POST request Status Code: {}",response.statusCode());

            return handleResponse(response, responseType);
        } catch (Exception e) {
            logger.error("Error sending POST request ",e);
            throw new ApiClientException("Error sending POST request: " + e.getMessage(), e);
        }
    }

    /**
     * Handles an HTTP response,checking the status code and deserializing the response body.
     *
     * @param response     the HTTP response to process
     * @param responseType the class type of expected response
     * @param <T>          the type of response body
     * @return the deserialized response body
     * @throws ApiClientException if response status is not 200 or JSON processing fails
     */
    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseType) {
        logger.debug("Handling HTTP response, Status Code: {},Body: {}",response.statusCode(),response.body());
        if (response.statusCode() == 200) {
            try {
                T result = objectMapper.readValue(response.body(), responseType);
                logger.debug("Deserialized response: {}",result);
                return result;
            } catch (JsonProcessingException e) {
                logger.error("Error processing JSON response",e);
                throw new ApiClientException("Error processing JSON response", e);
            }
        } else {
            logger.error("HTTP request failed with status code: {}, Body: {}",response.statusCode(),response.body());
            throw new ApiClientException("HTTP request failed with status code: " + response.statusCode() + ",Body: " + response.body());
        }
    }
}
