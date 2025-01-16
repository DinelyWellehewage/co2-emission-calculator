package com.dev.emissionCalculator;

import com.dev.emissionCalculator.context.ApplicationContext;
import com.dev.emissionCalculator.service.CO2EmissionService;
import com.dev.emissionCalculator.util.exception.ApiClientException;
import com.dev.emissionCalculator.util.exception.InvalidInputException;
import java.util.Map;

/**
 * CO2Calculator class serves as the main entry point for the CO2 emission calculator application
 * This application calculates the CO2 emission caused by traveling between two cities for a
 * specified transportation method.
 * The input parameters (start city, end city, transportation method) are provided as command-line arguments.
 *
 *
 * @author Dinely Shanuka
 */

public class CO2Calculator {

    /**
     * The main method pass arguments to calculate the CO2 emission.
     *
     * @param args the command line arguments specifying the trip details
     */


    public static void main(String[] args) {


        ApplicationContext applicationContext = new ApplicationContext();
        CO2EmissionService co2EmissionService = applicationContext.getCo2EmissionService();
        try {
            Map<String, String> arguments = applicationContext.getArgumentParser().parseArguments(args);

            String startCity = arguments.get("--start");
            String endCity = arguments.get("--end");
            String transportationMethod = arguments.get("--transportation-method");

            if (startCity == null || endCity == null || transportationMethod == null) {
                System.out.println("Usage: ./co2-calculator --start <startCity> --end <endCity> --transportation-method <vehicleType>");
                return;
            }
            String co2Emission = co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod);
            System.out.println("Your trip caused " + co2Emission + "kg of CO2-equivalent.");
        } catch (InvalidInputException exception) {
            System.out.println("Error: " + exception.getMessage());
        } catch (ApiClientException exception) {
            System.out.println("API Error: " + exception.getMessage());
        } catch (Exception exception) {
            System.out.println("An unexpected error occurred: " + exception.getMessage());
        }

    }



}
