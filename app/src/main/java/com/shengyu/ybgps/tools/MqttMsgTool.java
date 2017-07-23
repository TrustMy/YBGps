package com.shengyu.ybgps.tools;

import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.tools.bean.MqttMessageBean;

/**
 * Created by Trust on 2017/7/18.
 */

public class MqttMsgTool {
    public static MqttMessageBean  getMessage(int type,long serialNo,String data){
        return new MqttMessageBean(type, serialNo, data);
    }


    public static String getBean(int type,String json){
        Gson gson  =new Gson();
        switch (type){
            case CaConfig.typeGPS:
                break;

            case CaConfig.typeTrip:
                break;

            case CaConfig.typeAlarm:
                break;
        }
        return null;
    }
}
