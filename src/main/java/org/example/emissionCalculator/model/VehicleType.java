package org.example.emissionCalculator.model;

public enum VehicleType {

    DIESEL_CAR_SMALL(142),
    PETROL_CAR_SMALL(154),
    PLUGIN_HYBRID_CAR_SMALL(73),
    ELECTRIC_CAR_SMALL(50),
    DIESEL_CAR_MEDIUM(171),
    PETROL_CAR_MEDIUM(110),
    PLUGIN_HYBRID_CAR_MEDIUM(110),
    ELECTRIC_CAR_MEDIUM(58),
    DIESEL_CAR_LARGE(209),
    PETROL_CAR_LARGE(282),
    PLUGIN_HYBRID_CAR_LARGE(126),
    ELECTRIC_CAR_LARGE(73),
    BUS(27),
    TRAIN(6);

    private final int co2Emission;

    VehicleType(int co2Emission){
        this.co2Emission = co2Emission;
    }

    public int getCo2Emission(){
        return co2Emission;
    }
}
