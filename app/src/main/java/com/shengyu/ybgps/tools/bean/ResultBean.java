package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2017/7/14.
 */

public class ResultBean {

    /**
     * status : true
     * mainserver : 180.168.194.98:9001
     * backupserver : 180.168.194.98:9001
     * username : changan/yubei/1180
     * password : changan_020742686600
     */

    private boolean status;
    private String mainserver;
    private String backupserver;
    private String username;
    private String password;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMainserver(String mainserver) {
        this.mainserver = mainserver;
    }

    public void setBackupserver(String backupserver) {
        this.backupserver = backupserver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMainserver() {
        return mainserver;
    }

    public String getBackupserver() {
        return backupserver;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
