package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.model.response.LocationInfo;

import java.util.List;
import java.util.Scanner;

/**
 * UserInteractionService is responsible for interacting with the user via the console
 * It displays available coordinates and allows the user to select one by entering an index
 */
public class UserInteractionService {

    /**
     * Display a list of available coordinates along with their associated country and region
     * and allows the user to select one
     *
     * @param coordinates a list of location information containing coordinates and metadata
     * @return index of the selected coordinate from the list
     */

    public int displayCityCoordinates(List<LocationInfo> coordinates) {
        System.out.println("Available Coordinates with Country and Region");
        for (int i = 0; i < coordinates.size(); i++) {
            String country = coordinates.get(i).getCountry();
            String region = coordinates.get(i).getRegion();
            System.out.println(i + " - " + coordinates.get(i).getCoordinates()+" Country: "+country+" ; Region: "+region);
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


}
