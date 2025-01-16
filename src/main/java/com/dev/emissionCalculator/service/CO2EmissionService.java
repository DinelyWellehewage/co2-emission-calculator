package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.model.VehicleType;
import com.dev.emissionCalculator.model.response.LocationInfo;
import com.dev.emissionCalculator.util.exception.ApiClientException;
import com.dev.emissionCalculator.util.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * CO2EmissionService calculates the CO2 emissions based on distance between two cities
 * and the transportation method
 */
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

    /**
     * Calculates the CO2 emissions between cities for a specified transportation method
     *
     * @param startCity the start city
     * @param endCity the destination city
     * @param transportationMethod the mode of transportation
     * @return the calculated CO2 emissions as a formatted string
     */
    public String calculateCo2Emissions(String startCity, String endCity, String transportationMethod) {
        try {
            VehicleType vehicleType = handleTransportationMethod(transportationMethod);

            List<Double> selectedStartCityCoordinates = selectCityCoordinate(startCity);
            List<Double> selectedEndCityCoordinates = selectCityCoordinate(endCity);

            double distance = getDistance(selectedStartCityCoordinates, selectedEndCityCoordinates);
            double emission = vehicleType.calculateEmission(distance);

            return String.format("%.1f", emission);
        } catch (InvalidInputException exception) {
            throw new InvalidInputException("Invalid input: " + exception.getMessage());
        } catch (ApiClientException exception) {
            throw new ApiClientException("API Client Error" + exception.getMessage());
        } catch (Exception exception) {
            throw new RuntimeException("An unexpected Error occurred" + exception.getMessage());
        }

    }

    /**
     * Fetches and allows the user to select coordinated for a given city
     *
     * @param cityName the name of city
     * @return a list of selected coordinates for the city
     */

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

    /**
     * Calculates the distance between two set of coordinates
     *
     * @param selectedStartCityCoordinates the selected coordinates for start city
     * @param selectedEndCityCoordinates the selected coordinates for end city
     * @return the calculated distance between two cities
     */

    private double getDistance(List<Double> selectedStartCityCoordinates, List<Double> selectedEndCityCoordinates) {
        List<List<Double>> coordinates = new ArrayList<>();
        coordinates.add(selectedStartCityCoordinates);
        coordinates.add(selectedEndCityCoordinates);
        return distanceService.getDistanceMatrix(coordinates);

    }

    /**
     * Converts the transportation method string to a VehicleType enum
     *
     * @param transportationMethod the mode of transportation
     * @return the corresponding VehicleType enum
     * @throws InvalidInputException if transportation method is invalid
     */
    private VehicleType handleTransportationMethod(String transportationMethod) {
        try {
            return VehicleType.valueOf(transportationMethod.toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid transport method: " + transportationMethod);
        }
    }

}
