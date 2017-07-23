package com.shengyu.ybgps.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shengyu.ybgps.R;
import com.shengyu.ybgps.activitys.BaseActivty;


/**
 * Created by Trust on 2017/4/12.
 */
public class T {
    private static Toast toast;
    public static void showToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(BaseActivty.context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }



    public static void waitToast ( String msg ,int time)
    {

        View view= LayoutInflater.from(BaseActivty.context).inflate(R.layout.wait_toast, null);
        TextView txt=(TextView) view.findViewById(R.id.text);
        txt.setText(msg);

         toast = new Toast(BaseActivty.context);
//        toast.setGravity(Gravity.CENTER, 0, 160);
        toast.setDuration(time);
        toast.setView(view);
        toast.show();
    }


    public static  void errorToast(String msg ,int time)
    {
        View view=LayoutInflater.from(BaseActivty.context).inflate(R.layout.error_toast, null);
        TextView txt=(TextView) view.findViewById(R.id.error_toast_text);
        txt.setText(msg);
        toast = new Toast(BaseActivty.context);
//        toast.setGravity(Gravity.CENTER, 0, 160);
        toast.setDuration(time);
        toast.setView(view);
        toast.show();
    }
}
