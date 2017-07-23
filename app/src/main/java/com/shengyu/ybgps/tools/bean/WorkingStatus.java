package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/6/8.
 */

public class WorkingStatus {
    private String name;
    private int status;


    public WorkingStatus(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
