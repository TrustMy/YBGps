package com.shengyu.ybgps.sy.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Trust on 2017/5/9.
 */
public class GpsStrokeMessage extends ObdMessage {
    /**
     *开始时间  结束时间
     */
    private long fireOnTime ,fireOffTime;

    public long getFireOnTime() {
        return fireOnTime;
    }

    public void setFireOnTime(long fireOnTime) {
        this.fireOnTime = fireOnTime;
    }

    public long getFireOffTime() {
        return fireOffTime;
    }

    public void setFireOffTime(long fireOffTime) {
        this.fireOffTime = fireOffTime;
    }

    /**
     * 起点,终点纬度
     */
    private double fireOnLat = 0.0f,fireOffLat = 0.0f;

    public double getFireOnLat() {
        return fireOnLat;
    }

    public void setFireOnLat(double fireOnLat) {
        this.fireOnLat = fireOnLat;
    }

    public double getFireOffLat() {
        return fireOffLat;
    }

    public void setFireOffLat(double fireOffLat) {
        this.fireOffLat = fireOffLat;
    }

    /**
     * 起点,终点经度
     */
    private double fireOnLon = 0.0f,fireOffLon = 0.0f;

    public double getFireOnLon() {
        return fireOnLon;
    }

    public void setFireOnLon(double fireOnLon) {
        this.fireOnLon = fireOnLon;
    }

    public double getFireOffLon() {
        return fireOffLon;
    }

    public void setFireOffLon(double fireOffLon) {
        this.fireOffLon = fireOffLon;
    }

    /**
     * 是否定位
     */
    private boolean fixed = false;

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean getFixed() {
        return fixed;
    }

    private String tag ;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 引擎状态
     */
    private int engineStatus = 0;

    public void setEngineStatus(int engineStatus) {
        this.engineStatus = engineStatus;
    }

    public int getEngineStatus() {
        return engineStatus;
    }

    public GpsStrokeMessage(ObdMessageID messageId, boolean needSave) {
        super(messageId, needSave);
    }



    private byte stroke;

    public byte getStroke() {
        return stroke;
    }

    public void setStroke(byte stroke) {
        this.stroke = stroke;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = new Date(fireOnTime);
        Date endDate = new Date(fireOffTime);

        String startDates = sdf.format(startDate);
        String endDates = sdf.format(endDate);
        return String.format(Locale.CHINESE, "%s,  起点纬度 = %f, 起点经度 = %f ,%s," +
                "终点纬度= %f,终点经度 = %f",
                startDates,fireOnLat,fireOnLon,endDates,fireOffLat,fireOffLon);
    }



}
