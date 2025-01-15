package org.example.emissionCalculator.service;

import org.example.emissionCalculator.model.VehicleType;
import org.example.emissionCalculator.util.exception.ApiClientException;
import org.example.emissionCalculator.util.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CO2EmissionService {

    private CityService cityService;
    private DistanceService distanceService;
    private UserInteractionService userInteractionService;

    public CO2EmissionService(CityService cityService, DistanceService distanceService,
                              UserInteractionService userInteractionService) {
        this.cityService = cityService;
        this.distanceService = distanceService;
        this.userInteractionService = userInteractionService;
    }

    public String calculateCo2Emissions(String startCity, String endCity, String transportationMethod) {
        VehicleType vehicleType = handleTransportationMethod(transportationMethod);

        List<Double> selectedStartCityCoordinates = selectCityCoordinate(startCity);
        List<Double> selectedEndCityCoordinates = selectCityCoordinate(endCity);

        double distance = getDistance(selectedStartCityCoordinates, selectedEndCityCoordinates);
        double emission = vehicleType.calculateEmission(distance);

        return String.format("%.1f", emission);
    }

    private List<Double> selectCityCoordinate(String cityName) {
        List<List<Double>> cityCoordinates = cityService.getCityCoordinates(cityName);
        if (cityCoordinates.isEmpty()) {
            throw new InvalidInputException("No coordinates found for city: " + cityName);
        }
        System.out.println(cityName + " Coordinates ");
        int cityIndex = userInteractionService.displayCityCoordinates(cityCoordinates);
        return cityCoordinates.get(cityIndex);
    }


    private double getDistance(List<Double> selectedStartCityCoordinates, List<Double> selectedEndCityCoordinates) {
        List<List<Double>> coordinates = new ArrayList<>();
        coordinates.add(selectedStartCityCoordinates);
        coordinates.add(selectedEndCityCoordinates);
        double distance = distanceService.getDistanceMatrix(coordinates);
        return distance;
    }

    private VehicleType handleTransportationMethod(String transportationMethod) {
        try {
            return VehicleType.valueOf(transportationMethod.toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transport method: " + transportationMethod);
        }
    }

}
