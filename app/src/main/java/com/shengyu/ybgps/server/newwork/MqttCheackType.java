package com.shengyu.ybgps.server.newwork;

import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.server.TrustServer;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.TimeTool;
import com.shengyu.ybgps.tools.bean.TypeAlarmBean;
import com.shengyu.ybgps.tools.bean.TypeBean;
import com.shengyu.ybgps.tools.bean.TypeGpsBean;
import com.shengyu.ybgps.tools.bean.TypeTripBean;

/**
 * Created by Trust on 2017/7/18.
 */

public class MqttCheackType {
    private TrustServer trustServer;
    private String type;
    private Integer qos;

    public MqttCheackType(TrustServer trustServer) {
        this.trustServer = trustServer;
    }

    public void sendMessage(TypeBean typeBean){
        String message = null;
        if(typeBean != null){
            switch (typeBean.getTypes()){
                case CaConfig.typeGPS:
                    type = CaConfig.topicGps;
                    qos  = 1;
                    message = typeGPSMessage(typeBean);
                    break;

                case CaConfig.typeAlarm:
                    type = CaConfig.topicAlarm;
                    qos  = 2;
                    message = typeAlarmMessage(typeBean);
                    break;

                case CaConfig.typeTrip:
                    type = CaConfig.topicTrip;
                    qos  = 2;
                    message = typeTripMessage(typeBean);
                    break;
            }
            if(message !=null){
                trustServer.sendMsg(type,qos,message);
                L.i("trustServer sendMsg success");
            }
        }
    }

    public String typeGPSMessage(TypeBean typeBean){
        TypeGpsBean bean = (TypeGpsBean) typeBean;
//        String result = "GPS;2;77232937658;1170;"+bean.getGpsTime()+
//                ";1;29.617029;106.587460;187;"+bean.getSpeed()
//                +";225;4;;;;;;;;;;;;;";
//        String msg  = "GPS;2;77232937658;1170;20170718120000;1;29.617029;106.587460;187;0.0;225;4;;;;;;;;;;;;;";
        String gps = "GPS;"+bean.getSerialNo()+";"+bean.getTerminalId()+";"+bean.getDriver()+";"+
                bean.getGpsTime()+";1;"+bean.getLat()+";"+bean.getLon()+";"+
                bean.getAlt()+";"+bean.getSpeed()+";"+bean.getBear()+";"+bean.getEngineStatus()+
                ";;;;;;;;;;;;;";
        L.d("生成的 :"+gps);
        return gps;
    }

    public String typeAlarmMessage(TypeBean typeBean){
        TypeAlarmBean bean = (TypeAlarmBean) typeBean;
//        String alrm = "ALARM;9;3733887419;1181;20170704124141,29.621736,106.580213;4;1,68.45352;;;;;;;;;;;;;;;";

        String alarm = "ALARM;"+bean.getSerialNo()+";"+bean.getTerminalId()+";"+bean.getDriver()+
                ";"+bean.getGpsTime()+","+bean.getLat()+","+bean.getLon()+";4;1,"+bean.getSpeed()+";;;;;;;;;;;;;;;";
        L.d("生成的 alarm:"+alarm);
        return alarm;

    }

    public String typeTripMessage(TypeBean typeBean){
        TypeTripBean bean = (TypeTripBean) typeBean;
//        String trip = "TRIP;2;16669202523;1181;2017-07-04 12:30:00;0.000000;0.000000;2017-07-04 13:00:00;0.000000;0.000000;0;2017061216315";

        String trip = "TRIP;"+bean.getSerialNos()+";"+bean.getCarSerialNumber()+";"+bean.getDriveId()+";"+
                TimeTool.getGPSTime(bean.getFireOnTime())+
                ";"+bean.getFireOnLat()+";"+bean.getFireOnLon()+";"+TimeTool.getGPSTime(bean.getFireOffTime())+
                ";"+bean.getFireOffLat()+";"+bean.getFireOffLon()+";" +
                bean.getTripStatus()+";"+bean.getTripTag();
        L.d("生成的trip:"+trip);
        return trip;
    }
}
