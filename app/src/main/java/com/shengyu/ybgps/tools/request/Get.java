package com.shengyu.ybgps.tools.request;

import android.util.Log;

import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.interfaces.ResultCallBack;
import com.shengyu.ybgps.tools.request.ssl.TrustAllCerts;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Trust on 2017/7/3.
 */

public class Get {
    private OkHttpClient okHttpClient ;
    private Request.Builder builder;
    private ResultCallBack resultCallBack;


    public Get(ResultCallBack resultCallBack) {
        this.okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(),new TrustAllCerts())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        builder = new Request.Builder();
        this.resultCallBack = resultCallBack;
    }



    public void Request(String url , int type) {
//        int driverId =1180;
//        String imsi = "020742686600";
        Request request  = builder.get().url(url).build();
        executeResponse(request, type);
    }

    public void executeResponse(final Request request , final int type) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("lhh", "onFailure: "+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String json = response.body().string();
                    L.d("json:"+json);
                    resultCallBack.CallBeck(json,type, CaConfig.SUCCESS);
                }

            }
        });
    }





}
