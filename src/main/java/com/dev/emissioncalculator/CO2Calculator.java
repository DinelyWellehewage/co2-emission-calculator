package com.dev.emissioncalculator;

import com.dev.emissioncalculator.context.ApplicationContext;
import com.dev.emissioncalculator.service.CO2EmissionService;
import com.dev.emissioncalculator.util.exception.ApiClientException;
import com.dev.emissioncalculator.util.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * CO2Calculator class serves as the main entry point for the CO2 emission calculator application
 * This application calculates the CO2 emission caused by traveling between two cities for a
 * specified transportation method.
 * The input parameters (start city, end city, transportation method) are provided as command-line arguments.
 *
 * @author Dinely Shanuka
 */

public class CO2Calculator {

    private static final Logger logger = LoggerFactory.getLogger(CO2Calculator.class);

    public static void main(String[] args) {
        System.out.println("CO2 Emission Calculator");
        System.out.println("-------------------------");

        ApplicationContext applicationContext = new ApplicationContext();
        CO2EmissionService co2EmissionService = applicationContext.getCo2EmissionService();
        try {
            Map<String, String> arguments = applicationContext.getArgumentParser().parseArguments(args);

            String startCity = arguments.get("--start");
            String endCity = arguments.get("--end");
            String transportationMethod = arguments.get("--transportation-method");

            logger.debug("Parsed Arguments: startCity={}, endCity={}, transportationMethod={}",
                    startCity,endCity,transportationMethod);

            System.out.println("Entered Start City: " + startCity);
            System.out.println("Entered End City: " + endCity);
            System.out.println("Entered Transportation method: " + transportationMethod);
            if (startCity == null || endCity == null || transportationMethod == null) {
                logger.warn("Missing required arguments. Displaying usage instructions.");
                System.out.println("Usage: ./co2-calculator --start <startCity> --end <endCity> --transportation-method <vehicleType>");
                return;
            }
            logger.info("Calculating CO2 emisssions for startCity={}, endCity={}, transportMethod={}",
                    startCity,endCity,transportationMethod);
            String co2Emission = co2EmissionService.calculateCo2Emissions(startCity, endCity, transportationMethod);
            System.out.println("Your trip caused " + co2Emission + "kg of CO2-equivalent.");
        } catch (InvalidInputException exception) {
            logger.error("Invalid input error: {}",exception.getMessage(),exception);
            System.out.println("Error: " + exception.getMessage());
        } catch (ApiClientException exception) {
            logger.error("API client error: {}",exception.getMessage(),exception);
            System.out.println("API Error: " + exception.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error occurred: {}",exception.getMessage(),exception);
            System.out.println("An unexpected error occurred: " + exception.getMessage());
        }
    }
}
