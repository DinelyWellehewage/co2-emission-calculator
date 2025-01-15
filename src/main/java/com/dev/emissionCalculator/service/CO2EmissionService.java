package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.model.VehicleType;
import com.dev.emissionCalculator.model.response.LocationInfo;
import com.dev.emissionCalculator.util.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

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
        List<LocationInfo> cityCoordinates = cityService.getCityCoordinates(cityName);
        if (cityCoordinates.isEmpty()) {
            throw new InvalidInputException("No coordinates found for city: " + cityName);
        }
        System.out.println(cityName + " Coordinates ");
        int cityIndex = userInteractionService.displayCityCoordinates(cityCoordinates);
        LocationInfo selectedLocationInfo = cityCoordinates.get(cityIndex);
        return selectedLocationInfo.getCoordinates();
    }


    private double getDistance(List<Double> selectedStartCityCoordinates, List<Double> selectedEndCityCoordinates) {
        List<List<Double>> coordinates = new ArrayList<>();
        coordinates.add(selectedStartCityCoordinates);
        coordinates.add(selectedEndCityCoordinates);
        return distanceService.getDistanceMatrix(coordinates);

    }

    private VehicleType handleTransportationMethod(String transportationMethod) {
        try {
            return VehicleType.valueOf(transportationMethod.toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transport method: " + transportationMethod);
        }
    }

}
