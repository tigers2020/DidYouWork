package com.androidnerdcolony.didyouwork.objects;

/**
 * Created by pomkim on 7/1/17.
 */

public class WorkTimeObject {
    private int timeHour;
    private int timeMinetues;


    public WorkTimeObject(){}
    public WorkTimeObject(int timeHour, int timeMinetues) {
        this.timeHour = timeHour;
        this.timeMinetues = timeMinetues;
    }

    public int getTimeMinetues() {
        return timeMinetues;
    }

    public void setTimeMinetues(int timeMinetues) {
        this.timeMinetues = timeMinetues;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }
}
