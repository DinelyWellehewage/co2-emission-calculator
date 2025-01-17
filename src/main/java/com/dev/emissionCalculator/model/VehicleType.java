package com.dev.emissionCalculator.model;

/**
 * Enum representing various type of vehicles and their associated emission factors.
 * Modified values based on {@link <a href="https://www.gov.uk/government/publications/greenhouse-gas-reporting-
 * conversion-factors-2019">Conversion Factors Documentation</a>}
 */

public enum VehicleType {

    DIESEL_CAR_SMALL(new BasicVehicle(142)),
    PETROL_CAR_SMALL(new BasicVehicle(154)),
    PLUGIN_HYBRID_CAR_SMALL(new BasicVehicle(73)),
    ELECTRIC_CAR_SMALL(new BasicVehicle(50)),
    DIESEL_CAR_MEDIUM(new BasicVehicle(171)),
    PETROL_CAR_MEDIUM(new BasicVehicle(192)),
    PLUGIN_HYBRID_CAR_MEDIUM(new BasicVehicle(110)),
    ELECTRIC_CAR_MEDIUM(new BasicVehicle(58)),
    DIESEL_CAR_LARGE(new BasicVehicle(209)),
    PETROL_CAR_LARGE(new BasicVehicle(282)),
    PLUGIN_HYBRID_CAR_LARGE(new BasicVehicle(126)),
    ELECTRIC_CAR_LARGE(new BasicVehicle(73)),
    BUS_DEFAULT(new BasicVehicle(27)),
    TRAIN_DEFAULT(new BasicVehicle(6));

    private final Vehicle vehicle;

    VehicleType(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Calculates the emissions for a given distance based on the vehicle type.
     *
     * @param distance the distance traveled
     * @return the calculated emissions of CO2
     */

    public double calculateEmission(double distance) {
        return vehicle.calculateEmissions(distance);
    }
}
