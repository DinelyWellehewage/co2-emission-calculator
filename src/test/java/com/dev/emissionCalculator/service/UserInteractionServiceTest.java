package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.model.response.LocationInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

class UserInteractionServiceTest {

    private UserInteractionService userInteractionService;

    @BeforeEach
    void setUp() {
        userInteractionService = new UserInteractionService();
    }

    @Test
    void displayCityCoordinates_Success() {

        List<LocationInfo> locationInfos = List.of(
                new LocationInfo(List.of(10.007046, 53.576158), "Germany", "Hamburg"),
                new LocationInfo(List.of(13.407032, 52.524932), "Germany", "Berlin")
        );

        String input = "1";
        InputStream stream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            int selectedIndex = userInteractionService.displayCityCoordinates(locationInfos);
            Assertions.assertEquals(1, selectedIndex);
        } finally {
            System.setIn(stream);
        }
    }
    @Test
    void displayCityCoordinates_Failure() {

        List<LocationInfo> locationInfos = List.of(
                new LocationInfo(List.of(10.007046, 53.576158), "Germany", "Hamburg"),
                new LocationInfo(List.of(13.407032, 52.524932), "Germany", "Berlin")
        );

        String input = "invalid\n3\n0\n";
        InputStream stream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            int selectedIndex = userInteractionService.displayCityCoordinates(locationInfos);
            Assertions.assertEquals(0, selectedIndex);
        } finally {
            System.setIn(stream);
        }
    }

}