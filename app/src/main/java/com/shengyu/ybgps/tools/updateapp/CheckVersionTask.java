package com.shengyu.ybgps.tools.updateapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.R;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Trust on 2016/8/29.
 * 检查APK更新
 */
public class CheckVersionTask implements Runnable {
    private Context context;

    private InputStream is;

//    private static Logger logger = LoggerFactory.getLogger(CheckVersionTask.class);

    private final int UPDATA_NONEED = 0;

    private final int UPDATA_CLIENT = 1;

    private final int GET_UNDATAINFO_ERROR = 2;//

    private final int SDCARD_NOMOUNTED = 3;

    private final int DOWN_ERROR = 4;

    private UpdataInfo info;

    private String localVersion;

    public CheckVersionTask(Context context , String localVersion) {
        this.context = context;
        this.localVersion = localVersion;
    }

    public void run()
    {

        HttpURLConnection conn = null;
        try {

            String path =CaConfig.updateApp;
            Log.i("lhh","path"+path);
            URL url = new URL(path);

            conn = (HttpURLConnection) url

                    .openConnection();

            conn.setConnectTimeout(10*1000);

            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200)
            {
                // 从服务器获得一个输入流
                is = conn.getInputStream();
                //is = MainActivity.this.getAssets().open("update.xml");

            }
            Log.d("CheckVersionTask", "localVersion" + localVersion);
            info = UpdataInfoParser.getUpdataInfo(is);
            Log.i("lhh"," /checkVersion info:" +info.getVersion().toString());
            if (info.getVersion().equals(localVersion))
            {

                Log.i("lhh", "版本号相同");

                //Message msg = new Message();

                //msg.what = UPDATA_NONEED;

                //UpHandler.sendMessage(msg);

                // LoginMain();

            }
            else {

                Log.i("lhh", "版本号不相同 ");

                Message msg = new Message();

                msg.what = UPDATA_CLIENT;

                UpHandler.sendMessage(msg);

            }

        }
        catch (Exception e)
        {
            // TODO: 2016/8/30 取消通知 获取服务器失败  
//            Message msg = new Message();
//
//            msg.what = GET_UNDATAINFO_ERROR;
//
//            UpHandler.sendMessage(msg);

            e.printStackTrace();
            Log.i("lhh","excepter "+e.toString());

        } finally {
            if ( conn != null ) {
//                logger.info("Close connection for update");
                conn.disconnect();
            }
        }

    }

    Handler UpHandler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub

            super.handleMessage(msg);

            switch (msg.what) {

                case UPDATA_NONEED:

                    Toast.makeText(context.getApplicationContext(), "不需要更新",

                            Toast.LENGTH_SHORT).show();

                    break;

                case UPDATA_CLIENT:

                    //对话框通知用户升级程序

                    showUpdataDialog();

                    break;

                case GET_UNDATAINFO_ERROR:

                    //服务器超时

//                    Toast.makeText(context.getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_LONG).show();

                    break;

                case DOWN_ERROR:

                    //下载apk失败

                    Toast.makeText(context.getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();

                    break;

            }

        }

    };



    /*

	 *

	 * 弹出对话框通知用户更新程序

	 *

	 * 弹出对话框的步骤：

	 *  1.创建alertDialog的builder.

	 *  2.要给builder设置属性, 对话框的内容,样式,按钮

	 *  3.通过builder 创建一个对话框

	 *  4.对话框show()出来

	 */

    protected void showUpdataDialog()
    {

        AlertDialog.Builder builer = new AlertDialog.Builder(context);

        builer.setTitle("版本更新");

        builer.setMessage(info.getDescription());

        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ

        builer.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int which) {

                Log.i("lhh", "下载apk,更新");

                downLoadApk();

            }

        });

        builer.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int which) {

                // TODO Auto-generated method stub

                //do sth
            }

        });


        AlertDialog dialog = builer.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        /*
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    T.showToast(context,"请升级!");
                        return true;

                }
                return false;
            }
        });
        */
        dialog.show();

    }





    /*

    * 从服务器中下载APK

    */
    protected void downLoadApk()
    {

        final ProgressDialog pd;    //进度条对话框

        pd = new ProgressDialog(context);

        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        pd.setMessage("正在下载更新");

        pd.show();

        new Thread()
        {

            @Override

            public void run() {

                try {

                    File file = DownLoadManager.getFileFromServer(info.getUrl(), pd);

                    sleep(3000);

                    installApk(file);

                    pd.dismiss(); //结束掉进度条对话框

                }
                catch (Exception e)
                {

                    Message msg = new Message();

                    msg.what = DOWN_ERROR;

                    UpHandler.sendMessage(msg);

                    e.printStackTrace();

                }

            }}.start();

    }




    //安装apk

    protected void installApk(File file) {

        Intent intent = new Intent();

        //执行动作

        intent.setAction(Intent.ACTION_VIEW);

        //执行的数据类型

        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

        context.startActivity(intent);



    }




}
