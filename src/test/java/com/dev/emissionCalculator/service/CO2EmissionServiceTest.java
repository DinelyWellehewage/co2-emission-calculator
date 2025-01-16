package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.model.VehicleType;
import com.dev.emissionCalculator.model.response.LocationInfo;
import com.dev.emissionCalculator.util.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CO2EmissionServiceTest {

    private CityService cityService;
    private DistanceService distanceService;
    private UserInteractionService userInteractionService;
    private CO2EmissionService co2EmissionService;

    @BeforeEach
    void setUp() {
        cityService = Mockito.mock(CityService.class);
        distanceService = Mockito.mock(DistanceService.class);
        userInteractionService = Mockito.mock(UserInteractionService.class);
        co2EmissionService = new CO2EmissionService(cityService, distanceService, userInteractionService);
    }

    @Test
    void calculateCo2Emissions_Success() {
        String startCity = "Hamburg";
        String endCity = "Berlin";
        String transportationMethod = "diesel-car-medium";

        List<LocationInfo> hamburgCoordinates = List.of(
                new LocationInfo(
                        List.of(10.007046, 53.576158), "Germany", "Hamburg"
                )
        );
        List<LocationInfo> berlinCoordinates = List.of(
                new LocationInfo(
                        List.of(13.407032, 52.524932), "Germany", "Berlin"
                )
        );
        double distance = 585662.25;
        double expectedEmission = VehicleType.DIESEL_CAR_MEDIUM.calculateEmission(distance);

        when(cityService.getCityCoordinates(startCity)).thenReturn(hamburgCoordinates);
        when(cityService.getCityCoordinates(endCity)).thenReturn(berlinCoordinates);
        when(userInteractionService.displayCityCoordinates(hamburgCoordinates)).thenReturn(0);
        when(userInteractionService.displayCityCoordinates(berlinCoordinates)).thenReturn(0);
        when(distanceService.getDistanceMatrix(any())).thenReturn(distance);

        String emission = co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod);
        assertEquals(String.format("%.1f", expectedEmission), emission);
    }

    @Test
    void calculateCo2Emissions_Failure_StartCityNotFound() {
        String startCity = "InvalidCity";
        String endCity = "Berlin";
        String transportationMethod = "diesel-car-medium";
        when(cityService.getCityCoordinates(startCity)).thenReturn(List.of());

        InvalidInputException invalidInputException =
                assertThrows(InvalidInputException.class,
                        () -> co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod));
        String expectedResult = "No coordinates found for city: " + startCity;
        assertEquals(expectedResult, invalidInputException.getMessage());
    }

    @Test
    void calculateCo2Emissions_Failure_EndCityNotFound() {
        String startCity = "Hamburg";
        String endCity = "InvalidCity";
        String transportationMethod = "diesel-car-medium";
        when(cityService.getCityCoordinates(startCity)).thenReturn(List.of());

        InvalidInputException invalidInputException =
                assertThrows(InvalidInputException.class,
                        () -> co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod));
        String expectedResult = "No coordinates found for city: " + startCity;
        assertEquals(expectedResult, invalidInputException.getMessage());
    }

    @Test
    void calculateCo2Emissions_Failure_InvalidTransportation() {
        String startCity = "Hamburg";
        String endCity = "Berlin";
        String transportationMethod = "InvalidTransport";
        when(cityService.getCityCoordinates(startCity)).thenReturn(List.of());

        InvalidInputException invalidInputException =
                assertThrows(InvalidInputException.class,
                        () -> co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod));
        String expectedResult = "Invalid transport method: " + transportationMethod;
        assertEquals(expectedResult, invalidInputException.getMessage());
    }

}