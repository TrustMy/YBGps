package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/7/18.
 */

public class MqttMessageBean {
    private int type;
    private long serialNo;
    private String data;

    public MqttMessageBean(int type, long serialNo, String data) {
        this.type = type;
        this.serialNo = serialNo;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
