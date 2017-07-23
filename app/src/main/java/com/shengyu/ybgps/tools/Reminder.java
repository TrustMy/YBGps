package com.shengyu.ybgps.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;


import com.shengyu.ybgps.CaConfig;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Trust on 2016/11/3.
 */
public class Reminder {
    private Timer timer;
    private Context context;
    private int seconds;
    private boolean isStartRanging = false;
    private Handler gpsHandler;

    public Reminder(int seconds, Context context , Handler gpsHandler) {
        timer = new Timer();
       this.seconds =seconds;
        this.context =context;
        this.gpsHandler = gpsHandler;
    }
    public void stopReminder ()
    {
        timer.cancel();
    }
    public void startReminder()
    {
        timer.schedule(new RemindTask(), seconds * 60000);
    }

    public boolean getIsStartRanging (){return isStartRanging;};
    public void setIsStartRanging (boolean isStartRanging){this.isStartRanging = isStartRanging;};



    class RemindTask extends TimerTask {
        public void run() {
            Log.d("RemindTask", "Time's up!");

            setIsStartRanging(true);
            Intent intetn = new Intent();
            intetn.setAction("start");
            context.sendBroadcast(intetn);
            timer.cancel(); //Terminate the timer thread
            CaConfig.isOpenAutoClose = true;
            gpsHandler.sendEmptyMessage(CaConfig.StartAutoClose);
        }
    }



}