package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/5/8.
 */
public class SdkGpsMessage {
    private long gpsTime;
    private double lat,lon;
    private int status;

    public void setStatus(int v) {
        status = v;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 是否定位
     */
    private boolean fixed;

    public void setFixed(boolean fixed) {
        this.fixed = fixed;

        if (fixed) {
            status = status | 0x00000002;
        } else {
            status = status & 0xFFFFFFD;
        }
    }


    public SdkGpsMessage(long gpsTime, double lat, double lon) {
        this.gpsTime = gpsTime;
        this.lat = lat;
        this.lon = lon;
    }

    public long getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(long gpsTime) {
        this.gpsTime = gpsTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "gpsTime:"+gpsTime+"|lat:"+lat+"|lon:"+lon;
    }
}
