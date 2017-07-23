package com.shengyu.ybgps.tools.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arron.passwordview.PasswordView;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.R;
import com.shengyu.ybgps.activitys.BaseActivty;
import com.shengyu.ybgps.tools.L;

/**
 * Created by Trust on 2017/7/19.
 */

public class DialogTools implements PasswordView.PasswordListener, View.OnClickListener{
    PasswordView passwordView;
    EditText editText;
    Dialog dialog;
    public  void showSerialNumber(Activity activity){
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_serial_number,null);
//        passwordView = (PasswordView) view.findViewById(R.id.passwordView);
        editText = (EditText) view.findViewById(R.id.ed);
        Button btnChangeMode = (Button) view.findViewById(R.id.btn_change_mode);
        dialog = new Dialog(activity, R.style.customDialog);
        btnChangeMode.setOnClickListener(this);
//        passwordView.setPasswordListener(this);
        dialog.setContentView(view);
//          dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

        if(!activity.isFinishing()){
            dialog.show();
        }

    }

    public void dissDialog(){
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_change_mode) {
            String msg = editText.getText().toString().toString();
            if (msg == null || msg.equals("")) {
                Toast.makeText(BaseActivty.context, "输入错误", Toast.LENGTH_SHORT).show();
            }else{
                if(CaConfig.carSerialNumber == null){
                    CaConfig.carSerialNumber = msg;
                    dialogInterface.working(true);
                }
            }
//            passwordView.setMode(passwordView.getMode() == PasswordView.Mode.RECT ? PasswordView.Mode.UNDERLINE : PasswordView.Mode.RECT);
        }
    }

    @Override
    public void passwordChange(String changeText) {
        L.d("passwordChange:"+changeText);
    }

    @Override
    public void passwordComplete() {
        L.d("passwordComplete:");

    }

    @Override
    public void keyEnterPress(String password, boolean isComplete) {
        L.d("keyEnterPress:"+password+"|isComplete:"+isComplete);
    }

    public interface setWorking{
        void working(boolean status);
    } ;
    public setWorking dialogInterface;

}
