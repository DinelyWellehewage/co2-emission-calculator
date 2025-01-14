package org.example;

import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.service.CityService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        HttpClientService httpClientService = new HttpClientService();
        CityService cityService = new CityService(httpClientService);

        List<Double> berlin = cityService.getCityCoordinates("Berlin");

        System.out.println(berlin);
    }
}