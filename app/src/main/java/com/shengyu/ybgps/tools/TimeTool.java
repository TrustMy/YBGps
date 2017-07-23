package com.shengyu.ybgps.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Trust on 2017/4/10.
 * 获取时间工具类
 */
public class TimeTool {


    public static String getSystemTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date(System.currentTimeMillis());//获取当前时间
        String systemTime = formatter.format(dateTime);
        return systemTime;
    }

    public static long getSystemTimeDate(){
        return    System.currentTimeMillis();
    }

    public static String getGPSTime(long  time)
    {
        L.d("time:"+time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date(time);//获取当前时间
        String GPSTime = formatter.format(dateTime);
        return GPSTime;
    }

    public static String getAllStringTime(long  time){
        L.d("time:"+time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date dateTime = new Date(time);//获取当前时间
        String GPSTime = formatter.format(dateTime);
        return GPSTime;
    }


    public static String getGPSNumTime(long  time)
    {
        L.d("time:"+time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dateTime = new Date(time);//获取当前时间
        String GPSTime = formatter.format(dateTime);
        return GPSTime;
    }




    /**
     * 给定两个时间进行比较 如果小于或等于设定的 大的时间为准,相反小的时间为准
     * @return
     */
    public static long getCurrentTime(long baseTime,long base2Time){
        long time = 60000L;//设定的时间区间为 1分钟
        if (baseTime != 0 && base2Time != 0) {
            long jetLag = Math.abs(baseTime - base2Time);
            L.d("jetLag:"+jetLag);
            if(jetLag <= time){
                return base2Time;
            }else{
                return  baseTime;
            }
        }else{
            if(baseTime == 0 && base2Time == 0){
                L.e("baseTime base2Time = 0");
                return 0;
            }else if (base2Time == 0){
                L.e("base2Time = 0");
                return baseTime;
            }else{
                L.e("baseTime = 0");
                return base2Time;
            }
        }
    }


    public static long getType(){
        SimpleDateFormat formatter = new SimpleDateFormat("yMMddHHmm");
        Date dateTime = new Date(System.currentTimeMillis());//获取当前时间
        String systemTime = formatter.format(dateTime);
        long time  =  Long.parseLong(systemTime);
        return time;
    }
}
