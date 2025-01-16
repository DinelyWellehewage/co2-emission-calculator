package com.dev.emissionCalculator.context;

import com.dev.emissionCalculator.client.ApiClient;
import com.dev.emissionCalculator.service.CO2EmissionService;
import com.dev.emissionCalculator.service.CityService;
import com.dev.emissionCalculator.service.DistanceService;
import com.dev.emissionCalculator.service.UserInteractionService;


public class ApplicationContext {

    private ApiClient apiClient;
    private CityService cityService;
    private DistanceService distanceService;
    private UserInteractionService userInteractionService;
    private CO2EmissionService co2EmissionService;


    public ApplicationContext() {
        this.apiClient = new ApiClient();
        this.cityService = new CityService(apiClient);
        this.distanceService = new DistanceService(apiClient);
        this.userInteractionService = new UserInteractionService();
        this.co2EmissionService = new CO2EmissionService(cityService,distanceService,userInteractionService);
    }

    public CO2EmissionService getCo2EmissionService(){
        return co2EmissionService;
    }
}
