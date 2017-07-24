package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/7/19.
 */

public class TypeAlarmBean extends TypeBean {

    /**
     * lon : 121443.28352
     * gpsTime : 20170719111439
     * speed : 0.68399996
     * terminalId : 20742686600
     * bear : 4
     * serialNo : 39321
     * alt : 86
     * driver : 1180
     * lat : 31295.925470000002
     * type : 39320
     */

    private double lon;
    private String gpsTime;
    private double speed;
    private long terminalId;
    private int bear;
    private long serialNo;
    private int alt;
    private int driver;
    private double lat;
    private int type;
    private String carSerialNumber;

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public String getCarSerialNumber() {
        return carSerialNumber;
    }

    public void setCarSerialNumber(String carSerialNumber) {
        this.carSerialNumber = carSerialNumber;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setTerminalId(long terminalId) {
        this.terminalId = terminalId;
    }

    public void setBear(int bear) {
        this.bear = bear;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public void setDriver(int driver) {
        this.driver = driver;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLon() {
        return lon;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public double getSpeed() {
        return speed;
    }

    public long getTerminalId() {
        return terminalId;
    }

    public int getBear() {
        return bear;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public int getAlt() {
        return alt;
    }

    public int getDriver() {
        return driver;
    }

    public double getLat() {
        return lat;
    }

    public int getType() {
        return type;
    }
}
