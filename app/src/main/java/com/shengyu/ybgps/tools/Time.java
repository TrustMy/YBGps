package com.shengyu.ybgps.tools;

import android.os.Handler;
import android.os.Message;

import com.shengyu.ybgps.CaConfig;


/**
 * Created by Trust on 2016/8/30.
 * 开启一个线程  显示 页面计时
 */
public class Time {
    int hourNumber ;
    int minuteNumber  ;
    int secondsNumber ;
    int number;
    int i ;
     Thread thread;


    public void  Times(int hourNumber, int minuteNumber, int secondsNumber, int number, int i) {
        this.hourNumber = hourNumber;
        this.minuteNumber = minuteNumber;
        this.secondsNumber = secondsNumber;
        this.number = number;
        this.i = i;
    }

    public  void time (final int isWorking , final Handler handler )
   {
       if (isWorking == 1)
       {
           if(thread == null)
           {
               thread = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       while (isWorking == 1)
                       {
                           number++;
                           if (number < 60)
                           {
                               secondsNumber = number;//秒
                           } else if (number == 60)
                           {
                               number = 0;
                               secondsNumber = number;
                               if (minuteNumber < 60)
                               {
                                   minuteNumber += 1;
                               } else if (minuteNumber == 60)
                               {
                                   minuteNumber = 0;
                                   hourNumber += 1;
                               }
                           }
                           if (i == 0)
                           {
                               i = 1;
                               break;
                           }

                           try {
                               Thread.sleep(1000);
                               Message message = new Message();
                               message.what = CaConfig.Fence;
                               message.arg1 = secondsNumber;
                               message.arg2 = minuteNumber;
                               message.obj = hourNumber;
                               handler.sendMessage(message);
                           }
                           catch (InterruptedException e)
                           {
                               e.printStackTrace();
                           }
                       }
                   }
               });

               thread.start();
           }


       }
   }
}
