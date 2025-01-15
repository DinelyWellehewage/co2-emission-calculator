package com.dev.emissionCalculator.model.response;

import java.util.List;

public class LocationInfo {

    private List<Double> coordinates;
    private String country;
    private String region;

    public LocationInfo(List<Double> coordinates, String country, String region) {
        this.coordinates = coordinates;
        this.country = country;
        this.region = region;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
