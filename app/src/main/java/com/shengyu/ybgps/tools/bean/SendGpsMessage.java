package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/5/10.
 */
public class SendGpsMessage {

    /**
     * endLon : 0
     * startLat : 0
     * endTime : 0
     * startTime : 0
     * endLat : 0
     * startLon : 0
     * status :0;
     */

    private double endLon;
    private double startLat;
    private long endTime;
    private long startTime;
    private double endLat;
    private double startLon;
    private byte status;
    private String tag;
    private String carSerialNumber;

    public String getCarSerialNumber() {
        return carSerialNumber;
    }

    public void setCarSerialNumber(String carSerialNumber) {
        this.carSerialNumber = carSerialNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public double getEndLon() {
        return endLon;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }


    @Override
    public String toString() {
        return "startTime:"+startTime+"|startLat:"+startLat+"|startLon:"+startLon+"|endTime:"
                +endTime+"|endLat:"+endLat+"|endLon:"+endLon+"|status:"+status+"|tag:"+tag;
    }
}
