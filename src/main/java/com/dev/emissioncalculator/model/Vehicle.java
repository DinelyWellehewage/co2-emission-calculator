package com.dev.emissioncalculator.model;


/**
 * Abstract class representing a generic vehicle
 * Provides a template for calculating CO2 emissions based on distance traveled
 */
public abstract class Vehicle {

    /**
     * Abstract method to get CO2 emission factor for the vehicle
     *
     * @return the CO2 emission factor
     */
    public abstract int getCo2Emission();

    /**
     * Calculates the total CO2 emissions for a given distance traveled
     *
     * @param distance the distance traveled
     * @return the calulated CO2 emission
     */

    public double calculateEmissions(double distance) {
        return ((distance / 1000) * getCo2Emission()) / 1000;
    }

}
