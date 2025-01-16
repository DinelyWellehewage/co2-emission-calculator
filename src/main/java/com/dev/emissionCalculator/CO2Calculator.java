package com.dev.emissionCalculator;

import com.dev.emissionCalculator.client.ApiClient;
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

        try {
            Map<String, String> arguments = parseArguments(args);

            String startCity = arguments.get("--start");
            String endCity = arguments.get("--end");
            String transportationMethod = arguments.get("--transportation-method");

            if (startCity == null || endCity == null || transportationMethod == null) {
                System.out.println("Usage: ./co2-calculator --start <startCity> --end <endCity> --transportation-method <vehicleType>");
                return;
            }
            ApiClient apiClient = new ApiClient();
            CityService cityService = new CityService(apiClient);
            DistanceService distanceService = new DistanceService(apiClient);
            UserInteractionService userInteractionService = new UserInteractionService();

            CO2EmissionService co2EmissionService = new CO2EmissionService(cityService, distanceService, userInteractionService);
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

    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        for (String arg : args) {
            if (arg.contains("=")) {
                String[] keyValue = arg.split("=");
                if (keyValue.length == 2) {
                    argsMap.put(keyValue[0], keyValue[1]);
                }
            } else if (arg.startsWith("--")) {
                String key = arg;
                String value = null;
                int index = Arrays.asList(args).indexOf(arg);
                if (index + 1 < args.length && !args[index + 1].startsWith("--")) {
                    value = args[index + 1];
                }
                argsMap.put(key, value);
            }
        }
        return argsMap;
    }

}
