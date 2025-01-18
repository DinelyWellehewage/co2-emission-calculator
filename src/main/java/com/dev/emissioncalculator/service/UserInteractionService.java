package com.dev.emissioncalculator.service;

import com.dev.emissioncalculator.model.response.LocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

/**
 * UserInteractionService is responsible for interacting with the user via the console
 * It displays available coordinates and allows the user to select one by entering an index
 */
public class UserInteractionService {

    private static final Logger logger = LoggerFactory.getLogger(UserInteractionService.class);

    /**
     * Display a list of available coordinates along with their associated country and region
     * and allows the user to select one
     *
     * @param coordinates a list of location information containing coordinates and metadata
     * @return index of the selected coordinate from the list
     */

    public int displayCityCoordinates(List<LocationInfo> coordinates, String cityName) {
        logger.info("Displaying coordinates for city: {}", cityName);

        System.out.println("\nAvailable Coordinates for " + cityName + " with Country and Region");
        for (int i = 0; i < coordinates.size(); i++) {
            LocationInfo location = coordinates.get(i);
            System.out.println(String.format("%d - %s | Country: %s | Region: %s",
                    i + 1, location.getCoordinates(), location.getCountry(), location.getRegion()));
        }

        return getUserSelectedIndex(coordinates.size());
    }

    /**
     * Prompts the user to select a valid index from the displayed list of coordinates
     *
     * @param maxIndex the maximum valid index for selection
     * @return the valid index selected by the user
     */

    private int getUserSelectedIndex(int maxIndex) {
        //if only one coordinate is available, there is no need to prompt the user for input
        if (maxIndex==1){
            return 0;
        }

        Scanner scanner = new Scanner(System.in);
        int selectedIndex = -1;
        while (selectedIndex < 0 || selectedIndex >= maxIndex) {
            System.out.println(String.format("Select a coordinate (1 to %d):", (maxIndex)));
            String input = scanner.nextLine().trim();
            logger.debug("User input received: {}", input);
            if (!input.isEmpty() && input.matches("\\d+")) {
                selectedIndex = Integer.parseInt(input) - 1;
                if (selectedIndex < 0 || selectedIndex >= maxIndex) {
                    logger.warn("Invalid index selected: {}. Must be between 1 and {}", selectedIndex + 1, maxIndex);
                    System.out.println("Invalid Index.Please Try again.");
                }
            } else {
                logger.error("Invalid input provided: {}", input);
                System.out.println("Invalid input. Please enter a number");
            }
        }
        return selectedIndex;
    }


}
