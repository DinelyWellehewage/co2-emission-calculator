package com.dev.emissionCalculator;

import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.context.ApplicationContext;
import com.dev.emissionCalculator.service.CO2EmissionService;
import com.dev.emissionCalculator.service.CityService;
import com.dev.emissionCalculator.service.DistanceService;
import com.dev.emissionCalculator.service.UserInteractionService;
import com.dev.emissionCalculator.util.exception.ApiClientException;
import com.dev.emissionCalculator.util.exception.InvalidInputException;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CO2Calculator {


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
