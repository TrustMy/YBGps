package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/7/18.
 */

public class TypeTripBean extends TypeBean {


    /**
     * fireOffLat : 0
     * driveId : 0
     * fireOffLon : 0
     * fireOnLat : 0
     * fireOnTime : 1500364436187
     * serialNo : 39319
     * fireOffTime : 1500364506758
     * fireOnLon : 0
     * termnlId : 0
     * type : 39319
     * tripStatus : 1
     * tripTag : 2017071815535601
     */

    private long fireOffLat;
    private int driveId;
    private long fireOffLon;
    private long fireOnLat;
    private long fireOnTime;
    private long serialNo;
    private long fireOffTime;
    private long fireOnLon;
    private long termnlId;
    private int type;
    private int tripStatus;
    private String tripTag;
    private String carSerialNumber;

    public void setTermnlId(long termnlId) {
        this.termnlId = termnlId;
    }

    public String getCarSerialNumber() {
        return carSerialNumber;
    }

    public void setCarSerialNumber(String carSerialNumber) {
        this.carSerialNumber = carSerialNumber;
    }

    public void setFireOffLat(int fireOffLat) {
        this.fireOffLat = fireOffLat;
    }

    public void setDriveId(int driveId) {
        this.driveId = driveId;
    }

    public void setFireOffLon(int fireOffLon) {
        this.fireOffLon = fireOffLon;
    }

    public void setFireOnLat(int fireOnLat) {
        this.fireOnLat = fireOnLat;
    }

    public void setFireOnTime(long fireOnTime) {
        this.fireOnTime = fireOnTime;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public void setFireOffTime(long fireOffTime) {
        this.fireOffTime = fireOffTime;
    }

    public void setFireOnLon(int fireOnLon) {
        this.fireOnLon = fireOnLon;
    }

    public void setTermnlId(int termnlId) {
        this.termnlId = termnlId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTripStatus(int tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void setTripTag(String tripTag) {
        this.tripTag = tripTag;
    }

    public long getFireOffLat() {
        return fireOffLat;
    }

    public void setFireOffLat(long fireOffLat) {
        this.fireOffLat = fireOffLat;
    }

    public int getDriveId() {
        return driveId;
    }

    public long getFireOffLon() {
        return fireOffLon;
    }

    public void setFireOffLon(long fireOffLon) {
        this.fireOffLon = fireOffLon;
    }

    public long getFireOnLat() {
        return fireOnLat;
    }

    public void setFireOnLat(long fireOnLat) {
        this.fireOnLat = fireOnLat;
    }

    public long getFireOnTime() {
        return fireOnTime;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public long getFireOffTime() {
        return fireOffTime;
    }

    public long getFireOnLon() {
        return fireOnLon;
    }

    public void setFireOnLon(long fireOnLon) {
        this.fireOnLon = fireOnLon;
    }

    public long getTermnlId() {
        return termnlId;
    }

    public int getType() {
        return type;
    }

    public int getTripStatus() {
        return tripStatus;
    }

    public String getTripTag() {
        return tripTag;
    }
}
