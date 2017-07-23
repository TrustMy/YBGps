package com.shengyu.ybgps.sy.protocol;



import com.shengyu.ybgps.CaConfig;

import java.util.ArrayList;

/**
 * Created by Trust on 2017/6/2.
 * 采集信息  登录,杀死app,关闭gps开关
 */

public class SY_0500 implements IMessageBody {
    /**
     * 司机id
     * @return
     */

    private int  license = CaConfig.license;

    public int getLicense() {
        return license;
    }

    public void setLicense(int license) {
        this.license = license;
    }

    /**
     * 版本号
     */
    private short version = (short) (CaConfig.version * 10);

    public float getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    /**
     * 时间
     * @return
     */

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    private ArrayList<IAdditionalItem> additionals = null;

    public ArrayList<IAdditionalItem> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(ArrayList<IAdditionalItem> v) {
        additionals = v;
    }

    public void addAdditional(IAdditionalItem v) {
        if (additionals == null) {
            additionals = new ArrayList<IAdditionalItem>();
        }

        additionals.add(v);
    }


    @Override
    public byte[] writeToBytes() {
        MyBuffer buff = new MyBuffer();
        buff.putShort(version);
        buff.putInt(license);
        buff.putLong(time);

        readFromBytes(buff.array());
        return buff.array();
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
        MyBuffer buff = new MyBuffer(messageBodyBytes);
        setVersion(buff.getShort());
        setLicense(buff.getInt());
        setTime(buff.getLong());
    }

    @Override
    public String toString() {
        return "0500 : version:"+version+"driver:"+license+"|time:"+time;
    }
}
