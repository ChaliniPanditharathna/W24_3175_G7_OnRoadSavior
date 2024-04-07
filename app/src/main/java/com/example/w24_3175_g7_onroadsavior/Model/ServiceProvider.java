package com.example.w24_3175_g7_onroadsavior.Model;

public class ServiceProvider {
    private String id;
    private String location;
    private String breakdownType;
    private float rating;

    public ServiceProvider() {
    }

    public ServiceProvider(String id, String location, String breakdownType, float rating) {
        this.id = id;
        this.location = location;
        this.breakdownType = breakdownType;
        this.rating = rating;
    }

    // Getters and setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBreakdownType() {
        return breakdownType;
    }

    public void setBreakdownType(String breakdownType) {
        this.breakdownType = breakdownType;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}