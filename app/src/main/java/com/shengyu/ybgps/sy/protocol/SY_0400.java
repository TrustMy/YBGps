package com.shengyu.ybgps.sy.protocol;



import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.tools.L;

import java.util.ArrayList;

/**
 * Created by Trust on 2017/5/18.
 */

public class SY_0400 implements IMessageBody{
    /**
     * 起始信息
     */
    private int fireOnTime;
    private int fireOnLat,fireOnLon;

    public void setFireOnTime(int fireOnTime) {
        this.fireOnTime = fireOnTime;
    }

    public void setFireOnLat(int fireOnLat) {
        this.fireOnLat = fireOnLat;
    }

    public void setFireOnLon(int fireOnLon) {
        this.fireOnLon = fireOnLon;
    }

    /**
     * 终点信息
     */
    private int fireOffTime;
    private int fireOffLat,fireOffLon;

    public void setFireOffTime(int fireOffTime) {
        this.fireOffTime = fireOffTime;
    }

    public void setFireOffLat(int fireOffLat) {
        this.fireOffLat = fireOffLat;
    }

    public void setFireOffLon(int fireOffLon) {
        this.fireOffLon = fireOffLon;
    }


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
     * 唯一标示
     */
    private String tag ;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    /**
     * 行程状态
     */
    private byte status; //0  正在进行  1 是结束

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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

        buff.putInt(license);
        buff.putInt(fireOnTime);
        buff.putInt(fireOnLat);
        buff.putInt(fireOnLon);

        buff.putInt(fireOffTime);
        buff.putInt(fireOffLat);
        buff.putInt(fireOffLon);

        buff.putByte(status);

        buff.putString(tag);

        readFromBytes(buff.array());
        return buff.array();
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
        MyBuffer buff = new MyBuffer(messageBodyBytes);
        setLicense(buff.getInt());
        setFireOnTime(buff.getInt());
        setFireOnLat(buff.getInt());
        setFireOnLon(buff.getInt());
        setFireOffTime(buff.getInt());
        setFireOffLat(buff.getInt());
        setFireOffLon(buff.getInt());

        setStatus(buff.get());
        setTag(buff.getString());
        L.d("0400 readFromBytes :"+fireOnLat+"|fireOnLat"+"|fireOnLon:"+fireOnLon+"|tag:"+getTag()+"|status:"+status);

        L.d("0400:"+buff.getString().getBytes().length);
    }


    @Override
    public String toString() {
        return "fireOnTime:"+fireOnTime+"|fireOnLat:"+fireOnLat+"|fireOnLon:"+fireOnLon
                +"|fireOffTime:"+fireOffTime+"|fireOffLat:"+fireOffLat+"|fireOffLon:"+fireOffLon+"|status:"+status
                +"|tag:"+tag+"|司机id:"+license;
    }
}
