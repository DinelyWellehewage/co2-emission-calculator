package com.dev.emissionCalculator.service;

import com.dev.emissionCalculator.model.response.LocationInfo;

import java.util.List;
import java.util.Scanner;

public class UserInteractionService {

    public int displayCityCoordinates(List<LocationInfo> coordinates) {
        System.out.println("Available Coordinates: ");
        for (int i = 0; i < coordinates.size(); i++) {
            String country = coordinates.get(i).getCountry();
            String region = coordinates.get(i).getRegion();
            System.out.println(i + " - " + coordinates.get(i).getCoordinates()+" Country: "+country+" ; Region: "+region);
        }
        return getUserSelectedIndex(coordinates.size());
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


}
