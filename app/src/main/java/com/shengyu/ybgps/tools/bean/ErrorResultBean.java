package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/5/16.
 */

public class ErrorResultBean {


    /**
     * status : false
     * message : 该司机已注册
     */

    private boolean status;
    private String message;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
