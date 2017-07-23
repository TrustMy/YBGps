package com.shengyu.ybgps.sy.protocol;



import com.shengyu.ybgps.tools.L;

import java.util.ArrayList;

/**
 * Created by Trust on 2017/5/9.
 */
public class SY_0300 implements IMessageBody {
    /**
     * 年
     */
    private byte startYear,endYear;

    public void setEndYear(byte v) {
        endYear = v;
    }

    public final byte getEndYear() {
        return endYear;
    }


    public void setStartYear(byte v) {
        startYear = v;
    }

    public final byte getStartYear() {
        return startYear;
    }

    /**
     * 月
     */
    private byte startMonth ,endMonth;

    public void setStartMonth(byte v) {
        startMonth = v;
    }

    public final byte getStartMonth() {
        return startMonth;
    }

    public void setEndMonth(byte v) {
        endMonth = v;
    }

    public final byte getEndMonth() {
        return endMonth;
    }

    /**
     * 日
     */
    private byte startDate ,endDate;

    public void setStartDate(byte v) {
        startDate = v;
    }

    public final byte getStartDate() {
        return startDate;
    }

    public void setEndDate(byte v) {
        endDate = v;
    }

    public final byte getEndDate() {
        return endDate;
    }

    /**
     * 时
     */
    private byte startHour,endHour;

    public void setStartHour(byte v) {
        startHour = v;
    }

    public final byte getStartHour() {
        return startHour;
    }

    public void setEndHour(byte v) {
        endHour = v;
    }

    public final byte getEndHour() {
        return endHour;
    }

    /**
     * 分
     */
    private byte startMinute,endMinute;

    public void setStartMinute(byte v) {
        startMinute = v;
    }

    public final byte getStartMinute() {
        return startMinute;
    }

    public void setEndMinute(byte v) {
        endMinute = v;
    }

    public final byte getEndMinute() {
        return endMinute;
    }

    /**
     * 秒
     */
    private byte startSecond,endSecond;

    public void setStartSecond(byte v) {
        startSecond = v;
    }

    public final byte getStartSecond() {
        return startSecond;
    }

    public void setEndSecond(byte v) {
        endSecond = v;
    }

    public final byte getEndSecond() {
        return endSecond;
    }

    /**
     * 状态位
     */
    private int status;

    public void setStatus(int v) {
        status = v;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 纬度，以度为单位的纬度值乘以10的6次方，精确到百万 分之一度
     */
    private int startLatitude,endLatitude;

    public final int getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(int value) {
        startLatitude = value;
    }

    public final int getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(int value) {
        endLatitude = value;
    }

    /**
     * 经度，以度为单位的经度值乘以10的6次方，精确到百万 分之一度
     */
    private int startLongitude,endLongitude;

    public final int getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(int value) {
        startLongitude = value;
    }

    public final int getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(int value) {
        endLongitude = value;
    }





    /**
     * 是否定位
     */
    private boolean fixed;

    public void setFixed(boolean fixed) {
        this.fixed = fixed;

        if (fixed) {
            status = status | 0x00000002;
        } else {
            status = status & 0xFFFFFFD;
        }
    }

    public boolean getFixed() {
        return fixed;
    }

    /**
     * 是否引擎启动
     */
    private boolean accOn;

    public void setAccOn(boolean accOn) {
        this.accOn = accOn;

        if (accOn) {
            status = status | 0x00000001;
        } else {
            status = status & 0xFFFFFFFE;
        }
    }

    public boolean getAccOn() {
        return accOn;
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

        buff.putInt(status);
        buff.putInt(startLatitude);
        buff.putInt(startLongitude);
        buff.putInt(endLatitude);
        buff.putInt(endLongitude);
        buff.putByte(Byte.parseByte(String.valueOf(startYear), 16));
        buff.putByte(Byte.parseByte(String.valueOf(startMonth), 16));
        buff.putByte(Byte.parseByte(String.valueOf(startDate), 16));
        buff.putByte(Byte.parseByte(String.valueOf(startHour), 16));
        buff.putByte(Byte.parseByte(String.valueOf(startMinute), 16));
        buff.putByte(Byte.parseByte(String.valueOf(startSecond), 16));

        buff.putByte(Byte.parseByte(String.valueOf(endYear), 16));
        buff.putByte(Byte.parseByte(String.valueOf(endMonth), 16));
        buff.putByte(Byte.parseByte(String.valueOf(endDate), 16));
        buff.putByte(Byte.parseByte(String.valueOf(endHour), 16));
        buff.putByte(Byte.parseByte(String.valueOf(endMinute), 16));
        buff.putByte(Byte.parseByte(String.valueOf(endSecond), 16));

        if (additionals != null && additionals.size() > 0) {
            for (IAdditionalItem ad : additionals) {
                buff.putByte(ad.getAdditionalId());
                buff.putByte(ad.getAdditionalLength());
                buff.putByteArray(ad.writeToBytes());
            }
        }

        return buff.array();
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
        MyBuffer buff = new MyBuffer(messageBodyBytes);
        setStatus(buff.getInt());

        if ((getStatus() & 0x00000002) == 0x00000002) {
            setFixed(true);
        } else {
            setFixed(false);
        }

        if ((getStatus() & 0x00000001) == 0x00000001) {
            setAccOn(true);
        } else {
            setAccOn(false);
        }

        setStartLatitude(buff.getInt());
        setStartLongitude(buff.getInt());
        setEndLatitude(buff.getInt());
        setEndLongitude(buff.getInt());

        setStartYear(Byte.parseByte(String.format("%02X", buff.get())));
        setStartMonth(Byte.parseByte(String.format("%02X", buff.get())));
        setStartDate(Byte.parseByte(String.format("%02X", buff.get())));
        setStartHour(Byte.parseByte(String.format("%02X", buff.get())));
        setStartMinute(Byte.parseByte(String.format("%02X", buff.get())));
        setStartSecond(Byte.parseByte(String.format("%02X", buff.get())));

        setEndYear(Byte.parseByte(String.format("%02X", buff.get())));
        setEndMonth(Byte.parseByte(String.format("%02X", buff.get())));
        setEndDate(Byte.parseByte(String.format("%02X", buff.get())));
        setEndHour(Byte.parseByte(String.format("%02X", buff.get())));
        setEndMinute(Byte.parseByte(String.format("%02X", buff.get())));
        setEndSecond(Byte.parseByte(String.format("%02X", buff.get())));

        setAdditionals(new ArrayList<IAdditionalItem>());
        int pos = 28;
        while (buff.hasRemain()) {
            byte additionalId = buff.get();
            int additionalLength = (int) (0x000000FF & buff.get());
            byte[] additionalBytes = buff.gets(additionalLength);
            IAdditionalItem item = PositionAdditionalFactory.CreatePositionalFactory(additionalId, additionalBytes);
            if (item != null) {
                additionals.add(item);
            } else {

                L.e("未知的附加协议:" + additionalId + ",附加长度:" + additionalLength);
            }
            pos = pos + 2 + additionalLength;
        }
    }


    @Override
    public String toString() {
        return "startHour:"+startHour+"startMin:"+startMinute+"startLati:"
                +getStartLatitude()+"startLon:"+startLongitude+"|endHour:"+getEndHour()
                +"|endMin:"+getEndMinute() +"|endLati:" +getEndLatitude()+
                "|endLon:"+getEndLongitude();
    }
}
