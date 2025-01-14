package org.example.emissionCalculator;

import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.service.CO2EmissionService;
import org.example.emissionCalculator.service.CityService;
import org.example.emissionCalculator.service.DistanceService;

import java.util.Scanner;

public class CO2Calculator {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start city: ");
        String startCity = scanner.nextLine();

        System.out.println("Enter end city");
        String endCity = scanner.nextLine();

        System.out.println("Enter transportation method");
        String transportationMethod = scanner.nextLine();


        HttpClientService httpClientService = new HttpClientService();
        CityService cityService = new CityService(httpClientService);
        DistanceService distanceService = new DistanceService(httpClientService);
        CO2EmissionService co2EmissionService = new CO2EmissionService(cityService,distanceService);
        co2EmissionService.calculateCo2Emissions(startCity,endCity,transportationMethod);

    }
}
