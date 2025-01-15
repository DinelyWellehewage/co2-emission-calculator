package org.example.emissionCalculator.service;

import org.example.emissionCalculator.model.VehicleType;
import org.example.emissionCalculator.util.exception.ApiClientException;

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

    public double calculateCo2Emissions(String startCity, String endCity, String transportationMethod) {
        VehicleType vehicleType = handleTransportationMethod(transportationMethod);

        List<Double> selectedStartCityCoordinates = selectCityCoordinate(startCity);
        List<Double> selectedEndCityCoordinates = selectCityCoordinate(endCity);

        double distance = getDistance(selectedStartCityCoordinates, selectedEndCityCoordinates);

        double emission = vehicleType.calculateEmission(distance);
        System.out.println(emission);
        return emission;

    }

    private List<Double> selectCityCoordinate(String cityName) {
            List<List<Double>> cityCoordinates = cityService.getCityCoordinates(cityName);
            if (cityCoordinates.isEmpty()) {
                throw new ApiClientException("No coordinates found for city: " + cityName);
            }
            System.out.println(cityName + " Coordinates ");
            displayCityCoordinates(cityCoordinates);
            int cityIndex = getUserSelectedIndex(cityCoordinates.size());
            return cityCoordinates.get(cityIndex);
    }

    private int getUserSelectedIndex(int maxIndex) {
        Scanner scanner = new Scanner(System.in);
        int selectedIndex;
        while (true) {
            System.out.println("Select a coordinate (0 to " + maxIndex + "): (enter index):");
            try {
                selectedIndex = scanner.nextInt();
                if (selectedIndex >= 0 && selectedIndex < maxIndex) {
                    break;
                } else {
                    System.out.println("Invalid Index. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number");
                scanner.nextLine();
            }
        }
        return selectedIndex;
    }

    private VehicleType handleTransportationMethod(String transportationMethod) {
        try {
            return VehicleType.valueOf(transportationMethod.toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transport method: " + transportationMethod);
        }
    }

    private double getDistance(List<Double> selectedStartCityCoordinates, List<Double> selectedEndCityCoordinates) {
        List<List<Double>> coordinates = new ArrayList<>();
        coordinates.add(selectedStartCityCoordinates);
        coordinates.add(selectedEndCityCoordinates);
        double distance = distanceService.getDistanceMatrix(coordinates);
        return distance;
    }


    private void displayCityCoordinates(List<List<Double>> coordinates) {
        for (int i = 0; i < coordinates.size(); i++) {
            System.out.println(i + " - " + coordinates.get(i));
        }
    }


}
