package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2016/8/29.
 */
public class GpsMessgerBean {
    private  String Latitude;
    private  String Longitude;
    private  float Speed ;
    private  String Altitude ;
    private  String Bearing ;
    private  int num ;
    private  boolean isSpeed;
    private double Distance;
    private String Place;

    public GpsMessgerBean(String latitude, String longitude, float speed, String altitude, String bearing,  int num, boolean isSpeed ,double Distance ,String Place) {
        Latitude = latitude;
        this.Place= Place;
        Longitude = longitude;
        Speed = speed;
        Altitude = altitude;
        Bearing = bearing;

        this.num = num;
        this.isSpeed = isSpeed;
        this.Distance = Distance;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public float getSpeed() {
        return Speed;
    }

    public void setSpeed(float speed) {
        Speed = speed;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }

    public String getBearing() {
        return Bearing;
    }

    public void setBearing(String bearing) {
        Bearing = bearing;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSpeed() {
        return isSpeed;
    }

    public void setSpeed(boolean speed) {
        isSpeed = speed;
    }

}
