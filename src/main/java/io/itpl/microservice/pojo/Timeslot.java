package io.itpl.microservice.pojo;

public class Timeslot {
    private int startHour;
    private int startMinute;
    private int finishHour;
    private int finishMinute;

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(int finishHour) {
        this.finishHour = finishHour;
    }

    public int getFinishMinute() {
        return finishMinute;
    }

    public void setFinishMinute(int finishMinute) {
        this.finishMinute = finishMinute;
    }
}
