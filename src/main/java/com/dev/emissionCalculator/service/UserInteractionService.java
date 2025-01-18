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

    public int displayCityCoordinates(List<LocationInfo> coordinates,String cityName) {
        System.out.println();
        System.out.println(String.format("Available Coordinates for %s with Country and Region",cityName));
        for (int i = 0; i < coordinates.size(); i++) {
            LocationInfo location = coordinates.get(i);
            System.out.println(String.format("%d - %s | Country: %s | Region: %s",
                    i+1, location.getCoordinates(), location.getCountry(), location.getRegion()));
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
        int selectedIndex = -1;
        while (selectedIndex<0 || selectedIndex>=maxIndex) {
            System.out.println(String.format("Select a coordinate (1 to %d):",(maxIndex)));
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.matches("\\d+")){
                selectedIndex = Integer.parseInt(input)-1;
                if (selectedIndex<0 || selectedIndex>=maxIndex){
                    System.out.println("Invalid Index.Please Try again.");
                }
            }else{
                System.out.println("Invalid input. Please enter a number");
            }
        }
        return selectedIndex;
    }


}
