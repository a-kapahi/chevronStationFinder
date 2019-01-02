package com.example.chevron_stationfinder;

public class Prediction {
    private String description;
    private String place_id;

    public Prediction(String description, String place_id) {
        this.description = description;
        this.place_id = place_id;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace_id() {
        return place_id;
    }
}
