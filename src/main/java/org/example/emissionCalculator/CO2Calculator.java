package org.example.emissionCalculator;

import org.example.emissionCalculator.client.HttpClientService;
import org.example.emissionCalculator.service.CO2EmissionService;
import org.example.emissionCalculator.service.CityService;
import org.example.emissionCalculator.service.DistanceService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CO2Calculator {


    public static void main(String[] args) {

        //handleUserInput();
        Map<String, String> arguments = parseArguments(args);

        String startCity = arguments.get("--start");
        String endCity = arguments.get("--end");
        String transportationMethod = arguments.get("--transportation-method");

        if (startCity==null || endCity==null || transportationMethod==null){
            System.out.println("Usage: ./co2-calculator --start <startCity> --end <endCity> --transportation-method <vehicleType>");
            return;
        }

        HttpClientService httpClientService = new HttpClientService();
        CityService cityService = new CityService(httpClientService);
        DistanceService distanceService = new DistanceService(httpClientService);
        CO2EmissionService co2EmissionService = new CO2EmissionService(cityService,distanceService);
        double co2Emission = co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod);
        System.out.println("Your trip caused "+ co2Emission+ " of CO2-equivalent.");
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

    private static void handleUserInput() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start city: ");
        String startCity = scanner.nextLine();

        System.out.println("Enter end city");
        String endCity = scanner.nextLine();

        System.out.println("Enter transportation method");
        String transportationMethod = scanner.nextLine();

        HttpClientService httpClientService = new HttpClientService();
        CityService cityService = new CityService(httpClientService);
        DistanceService distanceService = new DistanceService(httpClientService);
        CO2EmissionService co2EmissionService = new CO2EmissionService(cityService, distanceService);
        double co2Emission = co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod);
        System.out.println("Your trip caused " + co2Emission + " of CO2-equivalent.");
    }
}
