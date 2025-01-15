package org.example.emissionCalculator.model;

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
    BUS(new BasicVehicle(27)),
    TRAIN(new BasicVehicle(6));

    private final Vehicle vehicle;

    VehicleType(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double calculateEmission(double distance){
        return vehicle.calculateEmissions(distance);
    }
}
