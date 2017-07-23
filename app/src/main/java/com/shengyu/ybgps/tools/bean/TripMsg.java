package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/6/13.
 */

public class TripMsg {
    private String trip,message;


    public TripMsg(String trip, String message) {
        this.trip = trip;
        this.message = message;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
