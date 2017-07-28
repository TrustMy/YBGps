package com.shengyu.ybgps.server.newwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;


import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.TrustSharedPreferences;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Trust on 2017/7/10.
 */

public class MqttCommHelper {
    protected MqttAndroidClient client;
    protected MqttConnectOptions conOpt;

    protected MqttSendControll mqttSendControll;

    private final int initConnectionError = 0x001,connectionError = 0x002;

    public static boolean pushStatus = false;

    public static boolean doConnect = true;


    public void setMqttSendControll(MqttSendControll mqttSendControll) {
        this.mqttSendControll = mqttSendControll;
    }

    protected String host  ;
    protected String name ;
    protected String passWord ;
    protected String myTopic = CaConfig.topicSubscription;
    protected String clientId ;
    protected String [] topics ;
    protected int[] qoss = {1,1};
    protected Context context;

    public MqttCommHelper(Context context) {
        this.context = context;
        iniServer();
    }

    private void iniServer() {
//        host ="tcp://"+ TrustSharedPreferences.getMqttMsg().getString("mainserver","180.168.194.98:9001");
//        userName = TrustSharedPreferences.getMqttMsg().getString("username","0");
//        passWord = TrustSharedPreferences.getMqttMsg().getString("password","0");
//        L.d("server:"+host+"|userName:"+userName+"|password:"+passWord);

    }

    public static boolean initMqttStatus = false;

    public boolean workStatus = false;



    public void initMqtt(){
        synchronized (this){
            clearHandler();
            if(!initMqttStatus){
                initMqttStatus= true;
                L.d("mqtt config :"+host+"|name:"+name+"|password:"+passWord);
                // 服务器地址（协议+地址+端口号）
                String uri = host;
                client = new MqttAndroidClient(context, uri, clientId);
                // 设置MQTT监听并且接受消息
                client.setCallback(mqttCallback);

                conOpt = new MqttConnectOptions();

                // 设置超时时间，单位：秒
                conOpt.setConnectionTimeout(10);
                // 心跳包发送间隔，单位：秒
                conOpt.setKeepAliveInterval(20);
                // 用户名
                conOpt.setUserName(name);
                // 密码
                conOpt.setPassword(passWord.toCharArray());
                //设置保存session 接收离线消息
                conOpt.setCleanSession(false);
                //自动重连
//                conOpt.setAutomaticReconnect(true);
                // last will message

                String message = "{\"terminal_uid\":\"" + clientId + "\"}";
                String topic = myTopic;
                Integer qos = 0;
                Boolean retained = false;
                if ((!message.equals("")) || (!topic.equals(""))) {
                    // 最后的遗嘱
                    try {
//                conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
                    } catch (Exception e) {
//                    doConnect = false;
                        iMqttActionListener.onFailure(null, e);
                    }
                }
                L.i("init mqtt ");
            }
        }
    }

    // MQTT监听并且接受消息
    protected MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            mqttSendControll.getPush(topic,message);
            L.d("messageId:"+client.acknowledgeMessage(message.getId()+""));
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            L.d("deliveryComplete : success");
            pushStatus = false;
            mqttSendControll.successSend();

        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
            doConnect = true;
            L.e("connectionLost error : "+arg0.getMessage());
            mqttSendControll.errorSend();
//            if(workStatus){
//                mqttSendControll.sendMessage();
//            }


        }
    };

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            doConnect = false;
            try {
                // 订阅myTopic话题
//                client.subscribe(myTopic,1);
                client.subscribe(topics,qoss);
                L.d("订阅主题:"+topics[0]);
            } catch (MqttException e) {
                e.printStackTrace();
            }
            L.d(" mqtt connection success conOpt.isAutomaticReconnect():"+conOpt.isAutomaticReconnect());

            mqttSendControll.clearSerialNo();
            mqttSendControll.sendMessage();
            clearHandler();
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            // 连接失败，重连
            L.e("connection onFailure :"+arg1.getMessage());
            mqttSendControll.errorSend();
            /*

            if(workStatus){
                mqttSendControll.sendMessage();
            }
            */

        }
    };

    public void clearHandler(){

    }

    /** 连接MQTT服务器 */
    public void doClientConnection() {
        if (doConnect) {
            if (!client.isConnected()) {
                try {
                    client.connect(conOpt, null, iMqttActionListener);
                    L.i("doClientConnection ");
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {
                L.e("doClientConnection  client.isConnected():" + client.isConnected() + "|" +
                        "isConnectIsNomarl():" + isConnectIsNomarl());
            }
        }
        mqttSendControll.clearSerialNo();
    }

    /** 判断网络是否连接 */
    public  boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            L.i("Mqtt intentType:"+name);
            return true;
        } else {
            L.i("Mqtt intentTypeError");
            return false;
        }
    }

    public  void publish(String topic ,Integer qos  ,String msg){
        synchronized (this){
            if(doConnect){
                doClientConnection();
                return;
            }

            if( client!=null){
                //端口  8878   注册
                Boolean retained = false;
                if(!pushStatus && msg != null){
                    try {
                        L.d("push message topic:"+topic+"|qos:"+qos+"|msg:"+msg);
                        client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                        L.d("push topic:"+topic+"|msg:"+msg);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void disMqtt(){
        try {
            client.disconnect();
            initMqttStatus = false;
            workStatus = false;
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setWorkStatus(boolean workStatus) {
        this.workStatus = workStatus;
    }
}
