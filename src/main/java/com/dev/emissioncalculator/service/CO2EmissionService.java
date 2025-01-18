package com.dev.emissioncalculator.service;

import com.dev.emissioncalculator.model.VehicleType;
import com.dev.emissioncalculator.model.response.LocationInfo;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import com.dev.emissioncalculator.util.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * CO2EmissionService calculates the CO2 emissions based on distance between two cities
 * and the transportation method
 */
public class CO2EmissionService {

    private static final Logger logger = LoggerFactory.getLogger(CO2EmissionService.class);

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
     * @param startCity            the start city
     * @param endCity              the destination city
     * @param transportationMethod the mode of transportation
     * @return the calculated CO2 emissions as a formatted string
     */
    public String calculateCo2Emissions(String startCity, String endCity, String transportationMethod) {
        try {
            VehicleType vehicleType = handleTransportationMethod(transportationMethod);
            logger.debug("Identified VehicleType: {}", vehicleType);

            List<Double> selectedStartCityCoordinates = selectCityCoordinate(startCity);
            logger.debug("Selected start city coordinates: {}", selectedStartCityCoordinates);

            List<Double> selectedEndCityCoordinates = selectCityCoordinate(endCity);
            logger.debug("Selected end city coordinates: {}", selectedEndCityCoordinates);

            double distance = getDistance(selectedStartCityCoordinates, selectedEndCityCoordinates);

            double emission = vehicleType.calculateEmission(distance);

            return String.format("%.1f", emission);
        } catch (InvalidInputException exception) {
            logger.error("Invalid input error: {}", exception.getMessage());
            throw new InvalidInputException("Invalid input: " + exception.getMessage());
        } catch (ApiClientException exception) {
            logger.error("API client error: {}", exception.getMessage());
            throw new ApiClientException("API Client Error" + exception.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error occured: {}", exception.getMessage());
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
            logger.warn("No coordinates found for city: {}", cityName);
            throw new InvalidInputException("No coordinates found for city: " + cityName);
        }
        int cityIndex = userInteractionService.displayCityCoordinates(cityCoordinates, cityName);
        LocationInfo selectedLocationInfo = cityCoordinates.get(cityIndex);
        logger.debug("User selected location: {}", selectedLocationInfo);
        return selectedLocationInfo.getCoordinates();
    }

    /**
     * Calculates the distance between two set of coordinates
     *
     * @param selectedStartCityCoordinates the selected coordinates for start city
     * @param selectedEndCityCoordinates   the selected coordinates for end city
     * @return the calculated distance between two cities
     */

    private double getDistance(List<Double> selectedStartCityCoordinates, List<Double> selectedEndCityCoordinates) {
        logger.debug("Calculating distance between coordiantes: {} and {}", selectedStartCityCoordinates, selectedEndCityCoordinates);
        List<List<Double>> coordinates = new ArrayList<>();
        coordinates.add(selectedStartCityCoordinates);
        coordinates.add(selectedEndCityCoordinates);

        double distanceMatrix = distanceService.getDistanceMatrix(coordinates);
        logger.debug("Calculated distance: {} meters", distanceMatrix);
        return distanceMatrix;
    }

    /**
     * Converts the transportation method string to a VehicleType enum
     *
     * @param transportationMethod the mode of transportation
     * @return the corresponding VehicleType enum
     * @throws InvalidInputException if transportation method is invalid
     */
    private VehicleType handleTransportationMethod(String transportationMethod) {
        logger.debug("Handling transportation method: {}", transportationMethod);
        try {
            return VehicleType.valueOf(transportationMethod.toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid transportation method: {}", transportationMethod);
            throw new InvalidInputException("Invalid transport method: " + transportationMethod);
        }
    }

}
