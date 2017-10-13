package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/7/18.
 */

public class TypeGpsBean extends TypeBean {

    /**
     * lon : 121443.18126
     * gpsTime : 20170719182347
     * speed : 0.612
     * terminalId : 20742686600
     * bear : 42.8
     * serialNo : 39321
     * alt : 90
     * driver : 1180
     * lat : 31295.83478
     * type : 39321
     * EngineStatus : 4
     */

    private double lon;
    private String gpsTime;
    private double speed;
    private long terminalId;
    private float bear;
    private long serialNo;
    private double alt;
    private int driver;
    private double lat;
    private int type;
    private int EngineStatus;

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

    public void setBear(float bear) {
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

    public void setEngineStatus(int EngineStatus) {
        this.EngineStatus = EngineStatus;
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

    public double getBear() {
        return bear;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public double getAlt() {
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

    public int getEngineStatus() {
        return EngineStatus;
    }
}
