package com.maltsev.greenhouse;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class Chart {
    private String title;
    private ChartType type;
    ArrayList<Entry> entries;
    private Float maxValue;
    private Float minValue;

    public Chart() {
    }

    public Chart(ChartType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChartType getType() {
        return type;
    }

    public void setType(ChartType type) {
        this.type = type;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }
}
