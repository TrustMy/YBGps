package com.shengyu.ybgps.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.database.DBHelperTrust;
import com.shengyu.ybgps.database.DBManagerTrust;
import com.shengyu.ybgps.tools.AndroidPermissionTool;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.T;
import com.shengyu.ybgps.tools.bean.ConfigBean;
import com.shengyu.ybgps.tools.bean.ElectronicFenceJsonBean;
import com.shengyu.ybgps.tools.bean.ErrorResultBean;
import com.shengyu.ybgps.tools.bean.ResultBean;
import com.shengyu.ybgps.tools.interfaces.ResultCallBack;
import com.shengyu.ybgps.tools.request.Get;
import com.shengyu.ybgps.tools.request.Post;

import org.json.JSONObject;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Trust on 2017/7/14.
 */

public abstract class BaseActivty extends AppCompatActivity {
    protected Post post;
    protected Get get;
    public static Activity activity ;
    public static Context context;
    public ResultCallBack resultCallBack = new ResultCallBack() {
        @Override
        public void CallBeck(Object obj, int type, int status) {
            resultCallBeack(obj,type,status);
        }
    };

    private DBHelperTrust dbHelperTrust;
    private DBManagerTrust dbManagerTrust ;

    private  SharedPreferences.Editor setJson;//保存电子围栏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniDate();
    }

    private  void iniDate(){
        context =  BaseActivty.this;
        dbHelperTrust = new DBHelperTrust(context);
        dbManagerTrust = new DBManagerTrust(context);
        post = new Post(this,resultCallBack);
        get = new Get(resultCallBack);
        setJson = context.getSharedPreferences("rlog", Activity.MODE_PRIVATE).edit();

        String msg = dbManagerTrust.selectConfigData();
        L.d("msg:"+msg);
        if (msg != null) {
            Gson gson = new Gson();
            ConfigBean configBean = gson.fromJson(msg,ConfigBean.class);
            CaConfig.maxSpeed = 0;
            if (configBean != null) {
                if(configBean.getMaxSpeed() != 0){
                    CaConfig.maxSpeed =  configBean.getMaxSpeed();
                }
                if(configBean.getCloseElectronicFenceTime() != 0){
                    CaConfig.autoClose = configBean.getCloseElectronicFenceTime();
                }
                if(configBean.getDisableClickEndTime() != 0){
                    CaConfig.DelayEndTime = configBean.getDisableClickEndTime();
                }
                L.d("config CaConfig.maxSpeed :"+CaConfig.maxSpeed +"|CaConfig.autoClose:"
                +CaConfig.autoClose+"|CaConfig.DelayEndTime:"+CaConfig.DelayEndTime);
            }
        }else{
            L.e("db config == null");
        }

    }

    public abstract void iniView();

    public void requestPostCallBeack(String url, Map<String,Object> map, int requestCode,int requestType,int requestHeader,String token){
     post.Request(url,map,requestCode,requestType,requestHeader,token);
    }

    public void requestGetCallBeack(String url,int type){
        get.Request(url,type);
    }

    //网络请求回调

    public void resultCallBeack(Object obj,int type,int status){
//        if(type != Config.updateApp && type != Config.pushId){
//            dissDialog();
//        }
        if(status == CaConfig.SUCCESS){
            successCallBeack(obj,type);
        }else{
            errorCallBeack(obj,type);
        }
    }
    public void successCallBeack(Object obj,int type){

        if(type == CaConfig.HttpAutoClose){
            String json = (String) obj;
            setJson.putString("jsonList",json);
            setJson.commit();
            Gson gson = new Gson();
            CaConfig.electronicFenceJsonBean = gson.fromJson(json,ElectronicFenceJsonBean.class);
        }
    }

    public void errorCallBeack(Object bean,int type){

    }



    /**
     * 检测 输入是否为空
     * @param editText
     * @param errorMsg
     * @return
     */
    protected  String checkMessage(EditText editText, String errorMsg){
        String msg = editText.getText().toString().trim();
        if(!msg.equals("")){
            return msg;
        }else{
            showErrorToast(errorMsg,1);
            return null;
        }
    }

    public void showErrorToast(String msg,int time){
        T.errorToast(msg,time*1000);
    }

}
