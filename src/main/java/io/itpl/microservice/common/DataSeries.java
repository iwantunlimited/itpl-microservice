package io.itpl.microservice.common;

import java.util.List;
import java.util.Objects;

public class DataSeries {
    private String name;
    private List<Double> data;
    private List<Integer> counts;
    private int countsTotal;
    private double seriesTotal;
    private int minimumCount;
    private int maximumCount;
    private double averageCount;
    private double minimumValue;
    private double maximumValue;
    private double averageValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public void setCounts(List<Integer> counts) {
        this.counts = counts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSeries that = (DataSeries) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getCountsTotal() {
        return countsTotal;
    }

    public void setCountsTotal(int countsTotal) {
        this.countsTotal = countsTotal;
    }

    public double getSeriesTotal() {
        return seriesTotal;
    }

    public void setSeriesTotal(double seriesTotal) {
        this.seriesTotal = seriesTotal;
    }

    public int getMinimumCount() {
        return minimumCount;
    }

    public void setMinimumCount(int minimumCount) {
        this.minimumCount = minimumCount;
    }

    public int getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(int maximumCount) {
        this.maximumCount = maximumCount;
    }

    public double getAverageCount() {
        return averageCount;
    }

    public void setAverageCount(double averageCount) {
        this.averageCount = averageCount;
    }

    public double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public double getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(double maximumValue) {
        this.maximumValue = maximumValue;
    }

    public double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(double averageValue) {
        this.averageValue = averageValue;
    }
}
