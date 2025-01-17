package com.dev.emissioncalculator.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatrixResponse {
    private List<List<Double>> distances;

    public List<List<Double>> getDistances() {
        return distances;
    }

    public void setDistances(List<List<Double>> distances) {
        this.distances = distances;
    }
}
