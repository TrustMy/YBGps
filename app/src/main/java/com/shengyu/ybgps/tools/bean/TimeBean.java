package com.shengyu.ybgps.tools.bean;

import android.content.SharedPreferences;

/**
 * Created by Trust on 2017/6/1.
 */

public class TimeBean {
    private int day,
            shi ,  fen ,
            miao ,  hourNumber,  minuteNumber,  secondsNumber,  number;

    private SharedPreferences prefs;

    public TimeBean(int day, int shi, int fen, int miao, int hourNumber, int minuteNumber, int secondsNumber, int number , SharedPreferences prefs) {
        this.day = day;
        this.shi = shi;
        this.fen = fen;
        this.miao = miao;
        this.prefs = prefs;
        this.hourNumber = hourNumber;
        this.minuteNumber = minuteNumber;
        this.secondsNumber = secondsNumber;
        this.number = number;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getShi() {
        return shi;
    }

    public void setShi(int shi) {
        this.shi = shi;
    }

    public int getFen() {
        return fen;
    }

    public void setFen(int fen) {
        this.fen = fen;
    }

    public int getMiao() {
        return miao;
    }

    public void setMiao(int miao) {
        this.miao = miao;
    }

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
}
