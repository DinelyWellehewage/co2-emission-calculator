package org.example.emissionCalculator.model.request;


import java.util.List;

public class MatrixRequest {

    private List<List<Double>> locations;
    private List<String> metrics;

    public List<List<Double>> getLocations() {
        return locations;
    }

    public void setLocations(List<List<Double>> locations) {
        this.locations = locations;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }
}
