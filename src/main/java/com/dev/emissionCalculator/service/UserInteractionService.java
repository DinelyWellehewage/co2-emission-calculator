package com.dev.emissionCalculator.service;

import java.util.List;
import java.util.Scanner;

public class UserInteractionService {

    public int displayCityCoordinates(List<List<Double>> coordinates) {
        System.out.println("Available Coordinates: ");
        for (int i = 0; i < coordinates.size(); i++) {
            System.out.println(i + " - " + coordinates.get(i));
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
