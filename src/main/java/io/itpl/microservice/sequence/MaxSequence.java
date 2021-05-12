package io.itpl.microservice.sequence;

public class MaxSequence {
    private String id;
    private long maxValue;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MaxSequence{" +
                "id='" + id + '\'' +
                ", maxValue=" + maxValue +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }
}
