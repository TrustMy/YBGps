package com.shengyu.ybgps.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.database.DBHelperTrust;
import com.shengyu.ybgps.database.DBManagerTrust;
import com.shengyu.ybgps.gpswork.GpsHelper;
import com.shengyu.ybgps.server.newwork.MqttCheackType;
import com.shengyu.ybgps.server.newwork.MqttCommHelper;
import com.shengyu.ybgps.server.newwork.MqttSendControll;
import com.shengyu.ybgps.server.newwork.ca.CAMqttCommHelper;
import com.shengyu.ybgps.sy.commn.EngineStatus;
import com.shengyu.ybgps.sy.message.CommonMessage;
import com.shengyu.ybgps.sy.message.GpsStrokeMessage;
import com.shengyu.ybgps.sy.message.ObdMessageID;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.T;
import com.shengyu.ybgps.tools.TimeTool;
import com.shengyu.ybgps.tools.bean.SendGpsMessage;
import com.shengyu.ybgps.tools.bean.TripMsg;
import com.shengyu.ybgps.tools.bean.TypeBean;
import com.shengyu.ybgps.tools.bean.TypeGpsBean;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Trust on 2017/7/17.
 */

public class TrustServer extends Service {
    private Context context = this;
    private Binder myBinder = new MsgBinder();
    private CAMqttCommHelper mqttCommHelper;

    private Handler gpsHandler;
    private GpsHelper gpsHelper;
    private MqttCheackType mqttCheackType;
    private MqttSendControll mqttSendControll;

    protected static ExecutorService threadPool = Executors.newCachedThreadPool();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
//        initMqtt();
        mqttCheackType = new MqttCheackType(this);
        mqttCommHelper =  new CAMqttCommHelper(context);
        mqttSendControll = new MqttSendControll(mqttCheackType,this);
        mqttCommHelper.setMqttSendControll(mqttSendControll);
        gpsHelper = new GpsHelper(mqttCheackType);
        threadPool.execute(gpsHelper);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gpsHandler = gpsHelper.getHandler();
        gpsHelper.setTrustServer(this);
        GpsHelper.myServerHandler = handler;
        dbManagerTrust = new DBManagerTrust(context);
    }

    public void initMqtt(){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
//                mqttCommHelper.initMqtt();
//                e.onNext(mqttCommHelper);
                mqttSendControll.sendMessage();
            }
        }).subscribe();
    }


    public void reConnection(){
        mqttCommHelper.doClientConnection();
    }


    public void sendMsg(String topic ,Integer qos,String msg){
        mqttCommHelper.publish(topic,qos,msg);
        L.d("trustServer sendMsg success");
    }

    public void disConnection(){
        mqttCommHelper.disMqtt();
    }

    public void startWork(){

        if (mqttCommHelper.workStatus) {

            L.i("Service is alreay in working status");

            return;
        }else{
            // 通知GPS线程开始采集GPS数据
            gpsHandler.sendEmptyMessage(CommonMessage.MSG_START_GPS_LISTENING);
            initMqtt();
//            mqttCommHelper.setWorkStatus(true);
        }
        mqttCommHelper.setWorkStatus(true);
        mqttSendControll.sendMessage();
        handler.sendEmptyMessageDelayed(CaConfig.GpsStroke,CaConfig.GpsStrockDelay);
    }

    public void endWork(){
        // 通知GPS线程停止采集GPS数据
        gpsHandler.sendEmptyMessage(CommonMessage.MSG_STOP_GPS_LISTENING);
        mqttCommHelper.setWorkStatus(false);
        CaConfig.appStatus = false;
    }





    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case CaConfig.LoopQuery:
                    foundHistory();
//                    sendTestGps();//测试
                    break;

                case CaConfig.GpsStroke:
                    sendStroke();
                    break;

            }
        }
    };

    public static Handler mainHandler;
    DBManagerTrust dbManagerTrust;
    public void  foundHistory(){
        TypeBean result = dbManagerTrust .selectFirst();
        Message message = Message.obtain();
        message.what = CaConfig.DbStatus;
        if(result != null){
            message.arg1 = 1;//有 提示点变红
            if(isNetworkConnected()){
                L.d("正在发送.....");
            }else{
                T.showToast("网络异常");
            }
            mqttSendControll.sendMessage();
            mqttSendControll.startHandler();
            L.d("foundHistory");
        }else{
            L.d("保存为null");
            message.arg1 = 0;//没有 提示点变绿
            if(!GpsHelper.gpsStarted){
                L.d("没有工作 数据库没有数据");

            }
        }
        mainHandler.sendMessage(message);
        handler.sendEmptyMessageDelayed(CaConfig.LoopQuery,CaConfig.DelayLoopQueryTime);
    }

    public void sendStroke() {
        if(dbManagerTrust.selectTrip() != null){
            TripMsg bean =  dbManagerTrust.selectTrip();
            String msg = bean.getMessage();
            if(msg!= null){
                mqttSendControll.sendMessage();
                Gson gson = new Gson();
                SendGpsMessage sendGpsMessage = gson.fromJson(msg,SendGpsMessage.class);
                L.d("文件message :"+sendGpsMessage.toString());

                Map<String,Object> map = new WeakHashMap<>();
                map.put("type",CaConfig.typeTrip);
                map.put("serialNo",TimeTool.getSystemTimeDate());
                map.put("termnlId",CaConfig.terminalId);
                map.put("driveId",CaConfig.license);
                map.put("fireOnTime",sendGpsMessage.getStartTime());
                map.put("fireOnLat",sendGpsMessage.getStartLat());
                map.put("fireOnLon",sendGpsMessage.getStartLon());


                map.put("fireOffTime",sendGpsMessage.getEndTime());
                map.put("fireOffLat",sendGpsMessage.getEndLat());
                map.put("fireOffLon",sendGpsMessage.getEndLon());

                map.put("tripStatus",sendGpsMessage.getStatus());
                map.put("tripTag",sendGpsMessage.getTag());
                map.put("carSerialNumber",sendGpsMessage.getCarSerialNumber());

                gpsHelper.SaveDate(CaConfig.typeTrip,TimeTool.getSystemTimeDate(),map);

            }else{
                L.e("msg == null");
            }
        }else{
            L.e("没有行程");
        }

    }




    public boolean isNetworkConnected() {
        return mqttCommHelper.isConnectIsNomarl();
    }



    public class MsgBinder extends Binder {

        public TrustServer getService(){
            return TrustServer.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }



}
