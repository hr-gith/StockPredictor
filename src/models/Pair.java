package models;

/**
 * Created by hamideh on 24/02/2017.
 */

import java.util.Date;

public class Pair {
    public Date key;
    public double value;

    //getters and setters

    public Date getKey() {
        return key;
    }
    public void setKey(Date key) {
        this.key = key;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    //constructor

    public Pair(Date key, double value) {
        super();
        this.key = key;
        this.value = value;
    }
}
