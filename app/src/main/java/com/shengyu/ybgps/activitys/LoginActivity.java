package com.shengyu.ybgps.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.R;
import com.shengyu.ybgps.database.DBHelperTrust;
import com.shengyu.ybgps.database.DBManagerTrust;
import com.shengyu.ybgps.tools.AndroidPermissionTool;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.T;
import com.shengyu.ybgps.tools.TrustSharedPreferences;
import com.shengyu.ybgps.tools.bean.ErrorResultBean;
import com.shengyu.ybgps.tools.bean.ResultBean;
import com.shengyu.ybgps.tools.bean.TypeBean;
import com.shengyu.ybgps.tools.bean.TypeGpsBean;
import com.shengyu.ybgps.tools.sy.SysTools;

import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

public class LoginActivity extends BaseActivty {
    private Context context = LoginActivity.this;
    private Activity activity = LoginActivity.this;

    private EditText userNameEt;
    private String driveId;
    private String terminalId;
    public static boolean status = false;
    private RelativeLayout mBg;
    private  ImageView mLoading;
    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AndroidPermissionTool androidPermissionTool = new AndroidPermissionTool();
        androidPermissionTool.checkPermission(activity);

        iniView();

        boolean login = getSharedPreferences("rlog", Activity.MODE_PRIVATE).getBoolean("login", false);

        boolean forceToRegister = getIntent().getBooleanExtra("forceToRegister", false);

        if (login && !forceToRegister ) {

            startActivity(new Intent(context, MainActivity.class));
            finish();

        }

    }



    @Override
    public void iniView() {
        userNameEt = (EditText) activity.findViewById(R.id.username);
        mBg = (RelativeLayout) findViewById(R.id.register_bg);
        mLoading = (ImageView) findViewById(R.id.register_loading);

    }
    @SuppressWarnings("ResourceType")
    public void onRegisterButton(View v){
        if(status){

        driveId = checkMessage(userNameEt,"司机id不能为空!");



        if(driveId != null){
            terminalId = checkTerminalId();
            if (terminalId != null) {
                mBg.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(context, R.animator.rotate);


                LinearInterpolator lir = new LinearInterpolator();
                animation.setInterpolator(lir);
                animation.setRepeatCount(1000);
                mLoading.startAnimation(animation);

                Map<String,Object> map = new WeakHashMap<>();
                map.put("driverId",driveId);
                map.put("imsi",terminalId);
                L.d("司机id:"+driveId+"|imsi:"+terminalId);
                requestPostCallBeack(CaConfig.login,map,CaConfig.loginTag,post.PUT,post.HeaderUrlencoded,null);
            }else{
                showErrorToast("请检查手机卡是否安装正确!",3);
                mBg.setVisibility(View.GONE);
                mLoading.clearAnimation();
            }
        }

        }else{
            T.showToast("请允许所有权限！");
            mBg.setVisibility(View.GONE);
            mLoading.clearAnimation();
        }

    }



    public String  checkTerminalId (){
        String terminalId = null;
        for( int i = 0; i < 10 && terminalId == null; i++ ) {
            terminalId = SysTools.getTerminalId();
            if(terminalId != null){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (terminalId == null) {
            return null;
        }else{
            return terminalId;
        }
    }

    @Override
    public void successCallBeack(Object obj, int type) {
        mBg.setVisibility(View.GONE);
        mLoading.clearAnimation();

        if (obj != null) {
            if(type == CaConfig.loginTag){

                if (terminalId != null && driveId != null) {

                    //修改登录状态

                    SharedPreferences.Editor editdf = context.getSharedPreferences("rlog", Activity.MODE_PRIVATE).edit();
                    editdf.putBoolean("login", true);
                    editdf.commit();

                    ResultBean bean = (ResultBean) obj;
                    if (bean.isStatus()) {
                        L.d("登录成功");


                    TrustSharedPreferences.setUserMesage(driveId,terminalId);

                    TrustSharedPreferences.setMeqttServer(CaConfig.mqttServer,
                            CaConfig.mqttServer,"mqtt_ca_user","mqtt_ca_user");
                    startActivity(new Intent(context,MainActivity.class));
                    finish();

                    }else{

                    }


                }
            }

        }
    }

    @Override
    public void errorCallBeack(Object bean, int type) {
        mBg.setVisibility(View.GONE);
        mLoading.clearAnimation();
        if(type == CaConfig.loginTag){
            showErrorToast(bean.toString(),3);
        }
    }

    public void onExt(View view){
        finish();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1:
                if(grantResults.length > 0 ){
                    for (int i = 0; i < grantResults.length; i++) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            status = false;
                            break;
                        }else{
                            status = true;
                        }
                    }

                    if (status) {
                        L.d("全部通过");
                    }else{
                        L.d("全部或一部分被拒绝");
                        T.showToast("有的权限被拒绝，请允许！");
                    }

                }

                break;
        }
    }
}
