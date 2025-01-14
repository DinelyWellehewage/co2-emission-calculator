package org.example.emissionCalculator.service;

import org.example.emissionCalculator.model.VehicleType;

public class CO2EmissionService {

    public double calculateEmissions(double distance, VehicleType vehicleType){
        return (distance* vehicleType.getCo2Emission())/1000;
    }
}
