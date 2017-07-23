package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2016/10/22.
 */
public class GpsLoaction {
    private double Lat;
    private double Lng;
    private String Name;

    public GpsLoaction(double lat, double lng, String name) {
        Lat = lat;
        Lng = lng;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}
