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
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Trust on 2017/5/11.
 */
public class Post {
    private String error = "连接异常!";
    private Gson gson;
    private ResultCallBack resultCallBack;
    public  int GET = 0x0001,POST = 0x0002,PUT = 0x0003,GET_NO_PARAMETER_Name = 0x0004;//GET_NO_PARAMETER_Name  GET请求 参数加名称直接 放参数
    public  final int HeaderNull = 0;
    public  final String TokenNull = null;
    public  final boolean addHeader = true,noAdd = false,addToken = true;
    public  final int HeaderJson = 0x00001,HeaderUrlencoded = 0x00002;
    private OkHttpClient okHttpClient ;
    private Request.Builder builder;
    private MediaType mediaType;
    private Activity activity;
    public Post(Activity activity, ResultCallBack resultCallBack) {
        this.activity  = activity;
        this.okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(),new TrustAllCerts())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        builder = new Request.Builder();
        this.resultCallBack = resultCallBack;
        gson = new Gson();
    }


    /**
     * 网络请求  并拆分
     * @param url
     * @param map  数据
     * @param requestCode  请求tag
     * @param requestType  请求类型
     * @param requestHeader 如果添加请求头 设置请求头种类 如果没有HeaderNull
     * @param token 如果添加token 设置token 如何没有TokenNull
     */
    public void Request(String url , Map<String , Object> map , int requestCode,int requestType,int requestHeader ,String token){
        String msg = null;
        String urls = url;
        Request request = null;
        L.d("requestType:"+requestType);
        switch (requestHeader){
            case HeaderJson:
                if (map != null) {
                    msg = new JSONObject(map).toString();
                }
                break;
            case HeaderUrlencoded:
                StringBuffer sb = null;
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    if(sb == null){
                        sb = new StringBuffer();
                        sb.append(entry.getKey()+"="+entry.getValue());
                    }else{
                        sb.append("&"+entry.getKey()+"="+entry.getValue());
                    }
                }
                msg = sb.toString();
                break;
        }

        if (msg != null) {
            L.d("Request 发送的json:"+msg);
        }
        if(requestType == GET || requestType == GET_NO_PARAMETER_Name){
            if (token != null) {
                builder.addHeader("Token",token);

            }
            if(map != null){
                StringBuffer sb = null;
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    if(sb == null){
                        sb = new StringBuffer();

                        if (requestType == GET_NO_PARAMETER_Name) {
                            if (entry.getValue() instanceof String){
                                sb.append(entry.getValue());
                            }else{
                                sb.append("?"+entry.getKey()+"="+entry.getValue());
                            }
                        }else{
                            if (entry.getValue() instanceof String){
                                sb.append("?"+entry.getKey()+"="+entry.getValue()+"");
                            }else{
                                sb.append("?"+entry.getKey()+"="+entry.getValue());
                            }
                        }
                    }else{
                        if (entry.getValue() instanceof String){
                            sb.append("&"+entry.getKey()+"="+entry.getValue()+"");
                        }else{
                            sb.append("&"+entry.getKey()+"="+entry.getValue());
                        }

                    }
                }

                L.d("get usrl :"+urls+ sb.toString());
                request =  builder.get().url(urls+ sb.toString()).build();
            }else{
                request =  builder.get().url(urls).build();
            }
        }else {
            if (msg != null) {
                if (requestHeader != HeaderNull){
                    request =  returnRequest(urls,requestType, requestHeader,msg,token);
                }else{
                    FormBody.Builder builder = new FormBody.Builder();
                    for (Map.Entry<String, Object> entry : map.entrySet()){
                        builder.add(entry.getKey(),entry.getValue()+"");
                    }
                    FormBody body = builder.build();
                    request  = new Request.Builder().url(urls).post(body).build();
                }
            }
        }
        if (request != null) {
            executeResponse(request,requestCode);
            L.d("url:"+request.url().toString());
        }

    }

    /**
     * 判断添加header 后判断是否添加token
     * @param url

     * @param requestHeader
     * @param msg
     * @return
     */

    private Request returnRequest(String url, int requestType,int requestHeader ,String msg ,String token) {
        Request request = null;
        RequestBody body = null;
        builder = new Request.Builder();
        body = returnBody(requestHeader,msg);

        if (body != null) {

            builder.url(url);
            if(token!= null){
                builder.addHeader("Token", token);
            }
            if (requestType == POST){
                builder.post(body);
            }else if (requestType == PUT){
                builder.put(body);
            }

            request = builder.build();
            L.d("request.headers().toString():"+request.headers().toString());
        }
        return request;
    }

    /**
     * 为body 添加header
     * @param requestHeader
     * @param requestMessage
     * @return
     */
    private RequestBody returnBody(int requestHeader,String requestMessage){
        RequestBody body = null;
        switch (requestHeader){
            case HeaderJson:
                mediaType = MediaType.parse("application/json");
                break;
            case HeaderUrlencoded:
                mediaType = MediaType.parse("application/x-www-form-urlencoded");
                break;
        }
        body = RequestBody.create(mediaType, requestMessage);
        return body;
    }









    public void executeResponse(final Request request , final int type) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("error:"+e.toString());
                L.e("onFailure:"+e.toString());
                Map<String,Object> map = new WeakHashMap<>();
                map.put("status",false);
                map.put("message", "请检查当前网络!");
                JSONObject json = new JSONObject(map);
                ErrorResultBean errorResultBean = gson.fromJson(json.toString(),ErrorResultBean.class);
                sendMessage(errorResultBean.getReason(),CaConfig.ERROR,type);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d("onResponse:"+response.toString());
                if(response.code() == 200){
                    String json = response.body().string();
                    L.d("json:"+json);
                    if(json != null && !json.equals("")){
                        ResultBean bean = gson.fromJson(json, ResultBean.class);
                        if(bean!=null && bean.isStatus()){
                            sendMessage(bean, CaConfig.SUCCESS,type);
                        }else{
                            if(bean == null){
                                sendMessage(error,CaConfig.ERROR,type);
                            }else{
                                ErrorResultBean errorResultBean = gson.fromJson(json,ErrorResultBean.class);
                                if(errorResultBean !=null){
                                    sendMessage(errorResultBean.getReason(),CaConfig.ERROR,type);
                                }else{
                                    sendMessage(error,CaConfig.ERROR,type);
                                }
                            }
                        }
                    }else{
                        sendMessage(error,CaConfig.ERROR,type);
                    }
                }else{
                    sendMessage("错误code:"+response.code(),CaConfig.ERROR,type);
                }
            }
        });
    }



    public void sendMessage(final Object result, final int status, final int type){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultCallBack.CallBeck(result,type,status);
            }
        });

    }



}
