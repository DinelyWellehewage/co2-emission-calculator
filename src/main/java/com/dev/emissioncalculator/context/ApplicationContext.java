package com.dev.emissioncalculator.context;

import com.dev.emissioncalculator.client.ApiClient;
import com.dev.emissioncalculator.service.CO2EmissionService;
import com.dev.emissioncalculator.service.CityService;
import com.dev.emissioncalculator.service.DistanceService;
import com.dev.emissioncalculator.service.UserInteractionService;
import com.dev.emissioncalculator.util.ArgumentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ApplicationContext initializes and manages the dependencies for the application.
 */

public class ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    private ApiClient apiClient;
    private CityService cityService;
    private DistanceService distanceService;
    private UserInteractionService userInteractionService;
    private CO2EmissionService co2EmissionService;
    private ArgumentParser argumentParser;

    /*
     * Initializes the application context and all required services.
     */

    public ApplicationContext() {
        logger.debug("Initializing ApplicationContext...");
        this.apiClient = new ApiClient();
        this.cityService = new CityService(apiClient);
        this.distanceService = new DistanceService(apiClient);
        this.userInteractionService = new UserInteractionService();
        this.co2EmissionService = new CO2EmissionService(cityService, distanceService, userInteractionService);
        this.argumentParser = new ArgumentParser();
    }

    /**
     * Provides the CO2EmissionService instance for emission calculation.
     */
    public CO2EmissionService getCo2EmissionService() {
        return co2EmissionService;
    }

    /**
     * Provides the ArgumentParser instance for parsing command-line arguments
     */

    public ArgumentParser getArgumentParser() {
        return argumentParser;
    }
}
