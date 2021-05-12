package io.itpl.microservice.common;

public class NumericRange {
    private double minimum;
    private double maximum;

    public static NumericRange of(double min,double max){
        NumericRange range = new NumericRange();
        range.setMinimum(min);
        range.setMaximum(max);
        return range;
    }
    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }
}
