package com.dev.emissionCalculator.model;

public abstract class Vehicle {

    public abstract int getCo2Emission();

    public double calculateEmissions(double distance) {
        return (distance * getCo2Emission()) / (1000 * 1000);
    }

}
