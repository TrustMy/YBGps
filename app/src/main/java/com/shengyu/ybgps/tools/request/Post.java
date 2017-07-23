package com.shengyu.ybgps.tools.request;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.bean.ErrorResultBean;
import com.shengyu.ybgps.tools.bean.ResultBean;
import com.shengyu.ybgps.tools.interfaces.ResultCallBack;
import com.shengyu.ybgps.tools.request.ssl.TrustAllCerts;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Trust on 2017/5/11.
 */
public class Post {
    private OkHttpClient okHttpClient ;
    private Request.Builder builder;
    private ResultCallBack resultCallBack;
    private Gson gson;
    private String error = "服务器返回为空!";
    private Activity activity;
    public Post(Activity activity, ResultCallBack resultCallBack) {
        this.activity  = activity;
        this.okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(),new TrustAllCerts())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        this.resultCallBack = resultCallBack;
        gson = new Gson();
    }


    public void Request(String url ,Map<String,Object> map,int type,boolean needHeader)
    {
       if(type != CaConfig.Version){
           StringBuffer sb = null;
           for (Map.Entry<String, Object> entry : map.entrySet()){
               if(sb == null){
                   sb = new StringBuffer();
                   sb.append(entry.getKey()+"="+entry.getValue());
               }else{
                   sb.append("&"+entry.getKey()+"="+entry.getValue());
               }
           }
           Log.d("lhh", "Request: "+map.toString().toString());
           needHeader(url,type,sb.toString(),needHeader);
       }else{
           headlerJson(url,new JSONObject(map).toString(),type);
       }
    }


    public void headlerJson(String url,String json,int type){
        MediaType JSON = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(JSON, json);
        builder = new Request.Builder();
        Request request = builder.url(url).post(body).build();
        executeResponse(request, type);
    }


    private void needHeader(String url, int type, String json ,boolean needHeader) {
        MediaType JSON = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(JSON, json);

        builder = new Request.Builder();
        Request request;

        if(needHeader){
            request    = builder.url(url).addHeader("Token", "").post(body)
                    .build();
        }else{
            request   = builder.url(url).post(body)
                    .build();
        }

        executeResponse(request, type);
    }

    public void executeResponse(final Request request , final int type) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("error:"+e.toString());
//                L.e("onFailure:"+e.toString());
//                Map<String,Object> map = new WeakHashMap<>();
//                map.put("status",false);
//                map.put("err", TrustException.getException(e));
//                JSONObject json = new JSONObject(map);
//                sendMessage(json.toString(),Config.ERROR,type);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d("onResponse:"+response.toString());
                if(response.code() == 200){
                    String json = response.body().string();
                    L.d("json:"+json);
                    if(json != null && !json.equals("")){
                        ResultBean bean = gson.fromJson(json, ResultBean.class);
                        if(bean!=null && bean.getStatus()){
                            sendMessage(bean, type,CaConfig.SUCCESS);
                        }else{
                            if(bean == null){
                                sendMessage(error,type,CaConfig.ERROR);
                            }else{
                                ErrorResultBean errorResultBean = gson.fromJson(json,ErrorResultBean.class);
                                if(errorResultBean !=null){
                                    sendMessage(errorResultBean.getMessage(),type,CaConfig.ERROR);
                                }else{
                                    sendMessage(error,type,CaConfig.ERROR);
                                }
                            }
                        }
                    }else{
                        sendMessage(error,type,CaConfig.ERROR);
                    }
                }else{
                    sendMessage("错误code:"+response.code(),type,CaConfig.ERROR);
                }
            }
        });
    }



    public void sendMessage(final Object result, final int status, final int type){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultCallBack.CallBeck(result,status,type);
            }
        });

    }



}
