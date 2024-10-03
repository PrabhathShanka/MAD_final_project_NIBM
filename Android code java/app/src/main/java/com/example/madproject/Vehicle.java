package com.example.madproject;


public class Vehicle {
    private String vehicleModel;
    private String licensePlate;

    public Vehicle() {
        // Default constructor required for Firestore
    }

    public Vehicle(String vehicleModel, String licensePlate) {
        this.vehicleModel = vehicleModel;
        this.licensePlate = licensePlate;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}

