package com.maltsev.greenhouse.ui.greenhouse.model;

public class Sensor {
    private String sensorTitle;
    private String sensorValue;

    public Sensor(String sensorTitle, String sensorValue) {
        this.sensorTitle = sensorTitle;
        this.sensorValue = sensorValue;
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
}
