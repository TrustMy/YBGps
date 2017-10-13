package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/5/16.
 */

public class ErrorResultBean {


    /**
     * status : false
     * reason : 该司机已注册
     */

    private boolean status;
    private String reason;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
