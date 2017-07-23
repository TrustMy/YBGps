package com.shengyu.ybgps.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.shengyu.ybgps.activitys.BaseActivty;

/**
 * Created by Trust on 2017/7/17.
 */

public class TrustSharedPreferences {

    public static void setUserMesage(String driverId,String terminalId){
        if(driverId != null && terminalId!=null && !driverId.equals("") && !terminalId.equals("")){
            SharedPreferences.Editor editor = BaseActivty.context.getSharedPreferences("UserMsg",
                    Context.MODE_PRIVATE).edit();
            editor.putString("terminalId", terminalId);
            editor.putString("driveId", driverId);
            editor.commit();
        }
    }

    public static String getMessage(String table,String key){
        SharedPreferences editor = BaseActivty.context.getSharedPreferences(table,
                Context.MODE_PRIVATE);
        return editor.getString(key, null);
    }


    public static void setMeqttServer(String mqttServer,String backupserver,String username,String password ){
        if ( mqttServer != null && !mqttServer.equals(""))  {
            SharedPreferences.Editor editor = BaseActivty.context.getSharedPreferences("Serever",
                    Context.MODE_PRIVATE).edit();
            editor.putString("mainserver", mqttServer);
            editor.putString("backupserver", backupserver);
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
        }
    }

    public static SharedPreferences getMqttMsg(){
        SharedPreferences editor = BaseActivty.context.getSharedPreferences("Serever",
                Context.MODE_PRIVATE);
        return editor;

    }

}
