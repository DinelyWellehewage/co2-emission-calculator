package org.example.emissionCalculator.service;

import org.example.emissionCalculator.model.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CO2EmissionService {

    private CityService cityService;
    private DistanceService distanceService;

    public CO2EmissionService(CityService cityService, DistanceService distanceService) {
        this.cityService = cityService;
        this.distanceService = distanceService;
    }


    public void calculateCo2Emissions(String startCity, String endCity, String transportationMethod) {
        List<List<Double>> startCityCoordinates = cityService.getCityCoordinates(startCity);
        List<List<Double>> endCityCoordinates = cityService.getCityCoordinates(endCity);
        System.out.println("Start City " + startCity + " Coordinates ");
        List<Double> selectedStartCityCoordinates = selectCityCoordinate(startCity, startCityCoordinates);
        System.out.println("End City " + endCity + " Coordinates ");
        List<Double> selectedEndCityCoordinates = selectCityCoordinate(endCity, endCityCoordinates);

        List<List<Double>> coordinates = new ArrayList<>();
        coordinates.add(selectedStartCityCoordinates);
        coordinates.add(selectedEndCityCoordinates);
        double distance = getDistance(coordinates);
        double emission = calculateEmissions(distance, VehicleType.DIESEL_CAR_MEDIUM);
        System.out.println(emission);

    }

    private double getDistance(List<List<Double>> coordinates) {

        double distance = distanceService.getDistanceMatrix(coordinates);
        return distance;
    }

    private List<Double> selectCityCoordinate(String city, List<List<Double>> coordinates) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(city + " Coordinates ");
        displayCityCoordinates(coordinates);
        System.out.println("Select a coordinate for " + city + "(enter index):");
        int cityIndex = scanner.nextInt();
        return coordinates.get(cityIndex);

    }

    private void displayCityCoordinates(List<List<Double>> coordinates) {
        for (int i = 0; i < coordinates.size(); i++) {
            System.out.println(i + " - " + coordinates.get(i));
        }
    }

    public double calculateEmissions(double distance, VehicleType vehicleType) {
        double emission = (distance * vehicleType.getCo2Emission()) / 1000;
        return emission / 1000;
    }


}
