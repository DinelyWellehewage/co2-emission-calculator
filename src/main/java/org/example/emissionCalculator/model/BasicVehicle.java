package org.example.emissionCalculator.model;

public class BasicVehicle extends Vehicle{

    private final int co2Emission;

    public BasicVehicle(int co2Emission){
        this.co2Emission = co2Emission;
    }


    @Override
    public int getCo2Emission() {
        return co2Emission;
    }
}
