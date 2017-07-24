package com.shengyu.ybgps.server.newwork;

import android.os.Handler;
import android.os.Message;

import com.shengyu.ybgps.activitys.BaseActivty;
import com.shengyu.ybgps.database.DBHelperTrust;
import com.shengyu.ybgps.database.DBManagerTrust;
import com.shengyu.ybgps.server.TrustServer;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.bean.TypeBean;

import java.util.LinkedList;

/**
 * Created by Trust on 2017/7/18.
 */

public class MqttSendControll {
    private DBManagerTrust dbManagerTrust;
    private MqttCheackType mqttCheackType;
    private TrustServer trustServer;
    public String SerialNo;
    public int rawId = -1;
    LinkedList taskQueue = new LinkedList();
    int taskQueueNum = 0;

    public MqttSendControll(MqttCheackType mqttCheackType , TrustServer trustServer) {
        this.mqttCheackType = mqttCheackType;
        this.trustServer= trustServer;
        this.dbManagerTrust = new DBManagerTrust(BaseActivty.context);
        startHandler();
    }

    public void startHandler() {
        handler.sendEmptyMessage(0x090);
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x090) {

                    synchronized (taskQueue) {
                        if (!taskQueue.isEmpty()) {
                            if(!MqttCommHelper.pushStatus){
                                taskQueue.remove(taskQueue.peek());
                                mqttSend();
                            }

                        }else{
                            if(!MqttCommHelper.initMqttStatus ){
                                L.e("初始化 mqtt");
                                trustServer.initMqtt();
                            }else{
                                if(!MqttCommHelper.doConnect){
                                    L.e("重连 mqtt");
                                    trustServer.initMqtt();
                                }
                            }
                        }

                }
            }
        }


    };

    public void sendMessage() {
        synchronized (taskQueue) {
            taskQueue.add(taskQueueNum++);
        }
    }


    public void successSend() {
        dbManagerTrust.deleteGps(rawId, SerialNo);
        clearHander();
        mqttSend();
    }

    public void errorSend() {
        clearSerialNo();
        if (SerialNo != null) {
            L.e("发送途中 失败");
        } else {
            L.e("连接失败");
        }

    }


    public void clearSerialNo() {
        L.d("clearSerialNo");
        SerialNo = null;
        rawId = -1;
    }

    public void clearHander(){
        handler.removeMessages(0x090);
    }

    private void mqttSend() {
        if(!MqttCommHelper.doConnect){
            synchronized (this) {
                TypeBean typeBean = dbManagerTrust.selectFirst();

                if (typeBean != null) {
                    L.d("MqttSendControll SerialNo:" + SerialNo + "|rawId:" + rawId + "|typeBean.getSerialNos():"
                            + typeBean.getSerialNos() + "|typeBean.getRawId():" + typeBean.getRawId());
                    if (SerialNo != typeBean.getSerialNos() && rawId != typeBean.getRawId()) {
                        SerialNo = typeBean.getSerialNos();
                        rawId = typeBean.getRawId();

                        if (SerialNo != null) {
                            L.d("MqttSendControll  SerialNo:" + SerialNo + "|rawId:" + rawId);
                            mqttCheackType.sendMessage(typeBean);
                        }

                    } else {
//                    sendMessage();//重新取
                        L.d("MqttSendControll  重新取");
                    }
                } else {
                    dbManagerTrust.clearRawId();
                    clearSerialNo();
                    L.e("typeBean == null");
                }
            }
        }else{
            L.e("mqttSend  链接异常");
            if(MqttCommHelper.initMqttStatus){
                L.e("mqttSend  connectionMqtt");
            }
            if (MqttCommHelper.doConnect){
                trustServer.reConnection();
                L.e("mqttSend  reConnection");
            }
        }

    }
}
