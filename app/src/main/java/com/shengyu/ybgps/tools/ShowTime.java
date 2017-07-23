package com.shengyu.ybgps.tools;

import android.content.SharedPreferences;


import com.shengyu.ybgps.tools.bean.TimeBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Trust on 2016/8/29.
 * 计算 开始时间 到当前时间的时差 并换算成对应的 时分秒 显示   开一个线程
 */
public class ShowTime {
    private int hourNumber ,minuteNumber,secondsNumber,number;

    public int getHourNumber() {
        return hourNumber;
    }

    public void setHourNumber(int hourNumber) {
        this.hourNumber = hourNumber;
    }

    public int getMinuteNumber() {
        return minuteNumber;
    }

    public void setMinuteNumber(int minuteNumber) {
        this.minuteNumber = minuteNumber;
    }

    public int getSecondsNumber() {
        return secondsNumber;
    }

    public void setSecondsNumber(int secondsNumber) {
        this.secondsNumber = secondsNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void Show(SharedPreferences prefs, int day, int shi , int fen , int miao , int hourNumber, int minuteNumber, int secondsNumber, int number )
    {

        doDate(new TimeBean(day,  shi ,  fen ,  miao ,  hourNumber,  minuteNumber,  secondsNumber,  number,prefs));
        /*
        String stime = prefs.getString("starttime", "0");//读取时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starttime = new Date(System.currentTimeMillis());//获取当前时间
        String endtime = formatter.format(starttime);
        try {
            Date s = df.parse(stime);
            long num = (starttime.getTime() - s.getTime());

            day = (int) (num / (24 * 60 * 60 * 1000));
            shi = (int) (num / (60 * 60 * 1000) - day * 24);
            fen = (int) ((num / (60 * 1000)) - day * 24 * 60 - shi * 60);
            miao = (int) (num / 1000 - day * 24 * 60 * 60 - shi * 60 * 60 - fen * 60);
            secondsNumber = miao;
            number = miao;
            minuteNumber = fen;
            hourNumber = shi;
            this.hourNumber = hourNumber;
            this.minuteNumber = minuteNumber;
            this.secondsNumber = secondsNumber;
            this.number = number;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        */
    }


    public void doDate(final TimeBean timeBean){
        final TimeBean tBean = timeBean;
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                if(tBean != null){
                    String stime = tBean.getPrefs().getString("starttime", "0");//读取时间
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date starttime = new Date(System.currentTimeMillis());//获取当前时间
                    String endtime = formatter.format(starttime);
                    try {
                        Date s = df.parse(stime);
                        long num = (starttime.getTime() - s.getTime());

                        tBean.setDay((int) (num / (24 * 60 * 60 * 1000)));
                        tBean.setShi((int) (num / (60 * 60 * 1000) - tBean.getDay() * 24));
                        tBean.setFen((int) ((num / (60 * 1000)) - tBean.getDay() * 24 * 60 - tBean.getShi() * 60));
                        tBean.setMiao((int) (num / 1000 - tBean.getDay() * 24 * 60 * 60 - tBean.getShi() * 60 * 60 - timeBean.getFen() * 60));
                        secondsNumber = tBean.getMiao();
                        number = tBean.getMiao();
                        minuteNumber = tBean.getFen();
                        hourNumber = tBean.getShi();

                        e.onNext(tBean);

                    }
                    catch (ParseException eMessage)
                    {
                        eMessage.printStackTrace();
                    }
                }
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                TimeBean bean = (TimeBean) o;

                hourNumber = bean.getShi();
                minuteNumber = bean.getFen();
                secondsNumber = bean.getMiao();
                number = bean.getMiao();
                L.d("onNext time :"+hourNumber+minuteNumber+secondsNumber);
            }

            @Override
            public void onError(Throwable e) {
                L.e(e.toString());
            }

            @Override
            public void onComplete() {
                L.d("success!");
            }
        };

        //建立连接
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }
}
