package com.dev.emissionCalculator.model;

/**
 * Represents a basic vehicle with a fixed CO2 emission factor
 */

public class BasicVehicle extends Vehicle {

    private final int co2Emission;

    public BasicVehicle(int co2Emission) {
        this.co2Emission = co2Emission;
    }

    @Override
    public int getCo2Emission() {
        return co2Emission;
    }
}
