package com.shengyu.ybgps.server.newwork.ca;

import android.content.Context;

import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.server.newwork.MqttCommHelper;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.TimeTool;
import com.shengyu.ybgps.tools.TrustSharedPreferences;


/**
 * Created by Trust on 2017/7/10.
 */

public class CAMqttCommHelper extends MqttCommHelper {

    public CAMqttCommHelper(Context context) {
        super(context);
        initConfig();
    }

    public void initConfig(){
        String mainserver = TrustSharedPreferences.getMessage("Serever","mainserver");
        String backupserver = TrustSharedPreferences.getMessage("Serever","backupserver");
        String userName = TrustSharedPreferences.getMessage("Serever","username");
        String pwd = TrustSharedPreferences.getMessage("Serever","password");
        if(mainserver != null){
            L.d("CAMqttCommHelper init");
            host = "tcp://" + mainserver;
            name = userName;
            passWord = pwd;
            clientId = CaConfig.terminalId!=0?CaConfig.terminalId+"": TimeTool.getSystemTimeDate()+"";
            topics  = new String[]{name};

            initMqtt();
        }
    }
}
