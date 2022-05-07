package com.maltsev.greenhouse.ui.greenhouse.model;

public class Sensor {
    private String sensorTitle;
    private String sensorValue;
    private String sensorNumberSystem;

    public Sensor(String sensorTitle, String sensorValue, String sensorNumberSystem) {
        this.sensorTitle = sensorTitle;
        this.sensorValue = sensorValue;
        this.sensorNumberSystem = sensorNumberSystem;
    }

    public Sensor() {
    }

    public String getSensorTitle() {
        return sensorTitle;
    }

    public void setSensorTitle(String sensorTitle) {
        this.sensorTitle = sensorTitle;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSensorNumberSystem() {
        return sensorNumberSystem;
    }

    public void setSensorNumberSystem(String sensorNumberSystem) {
        this.sensorNumberSystem = sensorNumberSystem;
    }
}
