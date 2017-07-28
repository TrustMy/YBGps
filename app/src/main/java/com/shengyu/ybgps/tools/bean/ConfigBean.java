package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/7/25.
 */

public class ConfigBean {
    /**
     * closeElectronicFenceTime : 30
     * disableClickEndTime  : 5
     * maxSpeed : 60.5
     */
    private int closeElectronicFenceTime;
    private int disableClickEndTime;
    private float maxSpeed;

    public void setCloseElectronicFenceTime(int closeElectronicFenceTime) {
        this.closeElectronicFenceTime = closeElectronicFenceTime;
    }

    public void setDisableClickEndTime(int disableClickEndTime) {
        this.disableClickEndTime = disableClickEndTime;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getCloseElectronicFenceTime() {
        return closeElectronicFenceTime;
    }

    public int getDisableClickEndTime() {
        return disableClickEndTime;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }
}
