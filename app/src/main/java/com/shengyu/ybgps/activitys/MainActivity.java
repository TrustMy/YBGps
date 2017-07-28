package com.shengyu.ybgps.activitys;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.R;
import com.shengyu.ybgps.database.DBManagerTrust;
import com.shengyu.ybgps.gpswork.GpsHelper;
import com.shengyu.ybgps.server.TrustServer;
import com.shengyu.ybgps.server.newwork.MqttCommHelper;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.Reminder;
import com.shengyu.ybgps.tools.ShowTime;
import com.shengyu.ybgps.tools.Time;
import com.shengyu.ybgps.tools.TrustCheckConfig;
import com.shengyu.ybgps.tools.TrustSharedPreferences;
import com.shengyu.ybgps.tools.bean.ElectronicFenceJsonBean;
import com.shengyu.ybgps.tools.bean.GpsMessgerBean;
import com.shengyu.ybgps.tools.bean.TypeBean;
import com.shengyu.ybgps.tools.bean.WorkingStatus;
import com.shengyu.ybgps.tools.dialog.DialogTools;
import com.shengyu.ybgps.tools.interfaces.SatelliteInterface;
import com.shengyu.ybgps.tools.other.UtilTool;
import com.shengyu.ybgps.tools.popupwindow.MenyPopupWindow;
import com.shengyu.ybgps.tools.sy.SysTools;
import com.shengyu.ybgps.tools.updateapp.CheckVersionTask;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MainActivity extends BaseActivty {
    private Context context = MainActivity.this;
    private Activity activity = MainActivity.this;
    private String terminalId;
    private Button End, Start;
    private TextView promptTv , GpsTv ;
    private Reminder reminder;
    private  ElectronicFenceJsonBean bean;
    private  TextView Gps;

    private DBManagerTrust dbManagerTrust;

    private  ArrayList<DialogMenuItem> testItems = new ArrayList<>();

    private  int secondsNumber = 0;

    private  int minuteNumber = 0;

    private  int hourNumber = 0;

    private  ImageView mTitle;

    private  TextView mhaosuWarning;

    private  ImageView imageViewWarning;

    private  TextView ToTime;

    private  int number = 0;

    private  int miao = 0, fen = 0, shi = 0, day = 0;

//    private  boolean isGone = false;//isGone  是否开始

    private RelativeLayout rlTopBar ;

    private  int i = 1;

    private SharedPreferences prefs , gpsDelaySPFGet,getUser;


    private  SharedPreferences.Editor To;

    private PopupWindow mPopupWindowMenu;

    private ListView mMenuListView;

    private boolean isStartRanging = false;//是否开始计算电子围栏


    private  int  Num = 0;//报警次数


    private ShowTime show;//计算 开始时间 到当前时间的时差 并换算成对应的 时分秒 显示

    private String localVersion;//版本号

    private Time time ;//开启一个线程  显示 页面计时

    public  static boolean HttpIsOk = false; //拉取 电子围栏的成功与否的状态

    private  TextView HttpElectronicFence;//电子围栏获取失败  就会显示 提示


    private ImageView mMenu;

    private BaseAnimatorSet bas_in;

    private BaseAnimatorSet bas_out;

    private List<String> mMenuList = new ArrayList<String>();

    private View mMenuLayout;

    private Toolbar toolBar ;

    private static int HttpElectronicFenceNum = 0;

    private TextView mShowVersion ;
    private PendingIntent operation;
    private int dbIsWorking = 0 ;//0为未工作  1是正在行驶   100是null没有保存

    private ImageView dbStatus;

    private  String license;

    private DialogTools dialogTools;

    private Handler GpsHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case CaConfig.Gps:
                    if(msg.obj!=null)
                    {
                        GpsMessgerBean bean = (GpsMessgerBean) msg.obj;
                        if (bean.getLongitude() != null && !"".equals(bean.getLongitude()) && bean.getLatitude() != null && !"".equals(bean.getLatitude()))
                        {
							//asdasdas

                            if (bean.getSpeed() >= 0 && bean.isSpeed()) {

                                Num = bean.getNum();
                                Log.d("MainActivity", "beam.getNum" + bean.getNum()+"getNum()"+Num);


//                        if (imageViewWarning != null || mhaosuWarning != null) {
                                imageViewWarning.setVisibility(View.VISIBLE);
                                mhaosuWarning.setVisibility(View.VISIBLE);
                                mTitle.setImageResource(R.mipmap.emoticon_21_512);
//                        }

                            } else {
                                if (imageViewWarning.getVisibility() == View.VISIBLE && mhaosuWarning.getVisibility() == View.VISIBLE) {
                                    imageViewWarning.setVisibility(View.GONE);
                                    mhaosuWarning.setVisibility(View.GONE);
                                    mTitle.setImageResource(R.mipmap.emoticon_512);
                                }
                            }

                            DecimalFormat df = new DecimalFormat("#0.0");
                            Gps.setText(df.format(bean.getSpeed()) + "公里/时");

                            if(!bean.getPlace().equals(""))
                            {
                                promptTv.setVisibility(View.VISIBLE);
                                promptTv.setText("与"+bean.getPlace()+" 距离:"+bean.getDistance()+"m");
                            }else
                            {
                                promptTv.setVisibility(View.VISIBLE);

                                if(isStartRanging)
                                {
                                    L.d("isStartRanging true :" +"执行了");
                                    promptTv.setText("未进入电子围栏");
                                }else
                                {
                                    L.d("isStartRanging false :" +"执行了");
                                    promptTv.setText("未启动电子围栏");
                                }

                            }





                        }else
                        {
                            Gps.setText("搜星不足,请稍后...");

                        }
                    }
                    break;



                case CaConfig.Fence:
                    if(HttpIsOk)
                    {
                        HttpElectronicFenceNum ++;
                        if(HttpElectronicFenceNum %3 ==0)
                        {
                            HttpElectronicFence.setVisibility(View.INVISIBLE);
                        }else
                        {
                            HttpElectronicFence.setVisibility(View.VISIBLE);
                        }
                    }else
                    {
                        HttpElectronicFence.setVisibility(View.INVISIBLE);
                    }


                    fen = msg.arg2;//分
                    miao = msg.arg1;//秒
                    shi = (int) msg.obj;//时
                    ToTime.setText(shi + "时" + fen + "分" + miao + "秒");

                    //小于5分钟 不能点  大于5分钟可以点击结束按钮
                    if(fen+ (shi * 10) >= CaConfig.DelayEndTime){
                        End.setBackgroundResource(R.drawable.btn_off);
                        End.setClickable(true);

                    }else{
                        End.setBackgroundResource(R.drawable.btn_on);
                        End.setClickable(false);
                    }




                    break;

                case CaConfig.CloseFence:
                    if((Boolean) msg.obj){
                        StopGpsWork();
                    }
                    break;

                case CaConfig.SelectHistoryData:
                    trustServer.foundHistory();
                    break;

                case CaConfig.delayEnd:
                    End.setBackgroundResource(R.drawable.btn_off);
                    End.setClickable(true);
//                    isGone = false;
                    break;

                case CaConfig.StartAutoClose:
                    promptTv.setText("未进入电子围栏");
                    CaConfig.isOpenAutoClose = true;
                    isStartRanging = true;
                    break;

                case CaConfig.DbStatus:
                    if(msg.arg1 == 0){//绿色 没有保存点
                        dbStatus.setBackgroundResource(R.mipmap.db_true);
                    }else{//红色 正在发送
                        dbStatus.setBackgroundResource(R.mipmap.db_false);
                    }
                    break;
            }



        }
    };
    SatelliteInterface satelliteInterface = new SatelliteInterface() {
        @Override
        public void getSatellite(int count) {
            Message message1 = Message.obtain();
            message1.what = CaConfig.Satellite;
            message1.arg1 = count;
            SatelliteHandler.sendMessage(message1);
        }
    };


    private Handler SatelliteHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CaConfig.Satellite:
//                    L.d("Satellite num :"+msg.arg1);
                    if(msg.arg1 >= 3)
                    {
                        GpsTv.setVisibility(View.GONE);

                    }else
                    {
                        GpsTv.setVisibility(View.VISIBLE);

                    }
                    break;
            }
        }
    };

    protected TrustServer trustServer = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            trustServer = ((TrustServer.MsgBinder)iBinder).getService();


            dbIsWorking = select();//查询工作状态
            if (dbIsWorking == 1)
            {
                CaConfig.appStatus = true; //app被杀死了
                L.d("CaConfig.appStatus:"+CaConfig.appStatus);

                String jsonList = prefs.getString("jsonList",null);
                if(jsonList != null){
                    Gson gson = new Gson();
                    CaConfig.electronicFenceJsonBean = gson.fromJson(jsonList,ElectronicFenceJsonBean.class);
                    HttpIsOk = false;
                }
                if(CaConfig.electronicFenceJsonBean!= null){
//                    isStartRanging = true;
//                    promptTv.setText("未进入电子围栏");
                }else{
                    HttpIsOk = true;
                    requestGetCallBeack(CaConfig.url_electronic_fence_json,CaConfig.HttpAutoClose);
                }
                trustServer.startWork();
            }else{
                L.d("error : dbIsWorking:"+dbIsWorking);
            }

            if(checkTerminalId()){
                L.d("连接mqtt");
                trustServer.reConnection();
            }else{
//                Toast.makeText(MainActivity.this, "请确保您的手机卡是否插好、是否处于飞行模式状态..", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            trustServer = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(trustServer == null){
            bindService(new Intent(MainActivity.this,TrustServer.class),serviceConnection, Context.BIND_AUTO_CREATE);
        }
        DBManagerTrust t = new DBManagerTrust<>(context);
        t.selectAllGps();
//        TypeBean bean = t.selectFirst();
//        L.d("bean:"+bean.getSerialNos()+"|rawId:"+bean.getRawId());


        rlTopBar = (RelativeLayout) findViewById(R.id.title);
        iniView();
        iniDate();

        checkIsWorking();


    }



    @Override
    public void iniView() {
        GpsHelper.mainGpsHanddler = GpsHandler  ;
        GpsHelper.satelliteInterface = satelliteInterface;
//        bo = (TextView) activity.findViewById(R.id.bo);
        GpsTv = (TextView) activity.findViewById(R.id.gps_signal);
        promptTv = (TextView) activity.findViewById(R.id.prompt_tv);

        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();
        // 创建Intent对象，action为android.intent.action.ALARM_RECEIVER
        Intent intent = new Intent("testDialog");
        operation = PendingIntent.getBroadcast(this, 0, intent, 0);

        dbStatus = (ImageView) activity.findViewById(R.id.home_db_status);

        mShowVersion = (TextView) activity.findViewById(R.id.show_version);
        HttpElectronicFence = (TextView) activity.findViewById(R.id.HttpElectronicFence);
        show = new ShowTime();

        Start = (Button) activity.findViewById(R.id.start);
        End = (Button) activity.findViewById(R.id.end);
        Gps = (TextView) activity.findViewById(R.id.GRS);

        mMenuList.add("注册");
//        mMenuList.add("查询是否保存Gps数据");
        ToTime = (TextView) activity.findViewById(R.id.totime);
        imageViewWarning = (ImageView) activity.findViewById(R.id.image);
        mhaosuWarning = (TextView) activity.findViewById(R.id.chaosu);
        mTitle = (ImageView) activity.findViewById(R.id.home_title);
        mMenu = (ImageView) activity.findViewById(R.id.image_menu);
        // TODO: 2016/8/29  主页面popupwindow
        new MenyPopupWindow(mMenuLayout,mMenuListView,mPopupWindowMenu).
                showPopupWidow(MainActivity.this,mMenu,getLayoutInflater()
                        ,mTitle,rlTopBar,mMenuList,GpsHandler);
    }


    protected void iniDate() {
        dialogTools = new DialogTools();
        dialogTools.dialogInterface = new DialogTools.setWorking() {
            @Override
            public void working(boolean status) {
                if (status) {
                    if(checkTerminalId()){
                        trustServer.startWork();
                        startGps();
                    }
                    dialogTools.dissDialog();
                }
            }
        };
        TrustServer.mainHandler = GpsHandler;
        dbManagerTrust = new DBManagerTrust(context);
        To = MainActivity.this.getSharedPreferences("rlog", Activity.MODE_PRIVATE).edit();
        prefs = MainActivity.this.getSharedPreferences("rlog", Activity.MODE_PRIVATE);
        gpsDelaySPFGet = MainActivity.this.getSharedPreferences("gpsDelaySPF",
                Activity.MODE_PRIVATE);

        //进入主页面5s启动查看是否有保存点  并且发送一次登录成功的信息
        GpsHandler.sendEmptyMessageDelayed(CaConfig.SelectHistoryData,5000);


        CaConfig.terminalId = Long.parseLong(TrustSharedPreferences.
                getMessage(CaConfig.SharedPreferencesUser,"terminalId"));

        license = TrustSharedPreferences.
                getMessage(CaConfig.SharedPreferencesUser,"driveId");
        CaConfig.license = Integer.parseInt(license);
        /*
        CaConfig.license = 1180;

        CaConfig.terminalId = Long.parseLong("020742686600");
        */


    }

    public void onStartButton(View v){
        dialogTools.showSerialNumber(activity);
    }



    public void onStopButton(View v){
        StopGpsWork();
    }

    private synchronized  void StopGpsWork() {
        if(reminder != null){
            reminder.stopReminder();
        }
        trustServer.endWork();

        HttpElectronicFence.setVisibility(View.INVISIBLE);


        isStartRanging = false;

        GpsTv.setVisibility(View.GONE);
        promptTv.setVisibility(View.GONE);
        bean = null; // 清空电子围栏点的坐标
        //重置 服务器拉取数据 失败 提示
        HttpIsOk = false;
        //重置 服务器拉取数据 失败 计数
        HttpElectronicFenceNum = 0;

        mMenu.setVisibility(View.VISIBLE);

        //恢复图片显示正常状态
        if (imageViewWarning.getVisibility() == View.VISIBLE && mhaosuWarning.getVisibility() == View.VISIBLE)
        {
            imageViewWarning.setVisibility(View.GONE);
            mhaosuWarning.setVisibility(View.GONE);
            mTitle.setImageResource(R.mipmap.emoticon_512);
        }

        //不可点击
        End.setBackgroundResource(R.drawable.btn_on);
        End.setClickable(false);
        Start.setBackgroundResource(R.drawable.btn_off);
        Start.setClickable(true);

        //速度显示
        Gps.setText(0 + "公里/时");

        //重置计时器
        i = 0;
        hourNumber = 0;
        minuteNumber = 0;
        secondsNumber = 0;
        number = 0;


        //添加工作单页面
//        testItems.add(new DialogMenuItem("人员： ", R.mipmap.ic_winstyle_artist));
        testItems.add(new DialogMenuItem("任务状态：已完成！", R.mipmap.ic_winstyle_favor));
        testItems.add(new DialogMenuItem("耗时：" + shi + "时" + fen + "分" + miao + "秒", R.mipmap.ic_winstyle_album));
        testItems.add(new DialogMenuItem("超速：" + Num + "次", R.mipmap.ic_winstyle_share));
        final NormalListDialog dialog = new NormalListDialog(this, testItems);
        dialog.title("工作单").showAnim(bas_in).dismissAnim(bas_out).show();


        //修改工作状态
        To.putBoolean("isWorking", false);

        To.commit();

        time();

        testItems.clear();

        //重置超速次数
        Num = 0;
        time.Times(hourNumber,minuteNumber,secondsNumber,number,i);
        time.time(dbIsWorking,GpsHandler);
        time=null;



        GpsHandler.removeMessages(CaConfig.StartAutoClose);



        dbManagerTrust.update(0);

        dbIsWorking =  select();
        if(dbIsWorking == 100){
            L.d("db ==  null");
        }else{
            L.d("dbIsWorking :"+dbIsWorking);
        }

        CaConfig.isOpenAutoClose = false;
    }




    private boolean checkTerminalId() {
        //循环读取 terminalId  有的手机读取一次有可能读取不到
        for (int i = 0; i < 10 && terminalId == null; i++)
        {
            terminalId = SysTools.getTerminalId();
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }


        //验证是否 插卡和系统保存的sim 设备号一致
        String terminalIdOld = TrustSharedPreferences.getMessage(CaConfig.SharedPreferencesUser,"terminalId");
        if (terminalIdOld != null) {
            if (terminalId == null  || !terminalId.equals(terminalIdOld) || TrustCheckConfig.getAirplaneMode(activity))
            {
                Toast.makeText(MainActivity.this, "请确保您的手机卡是否插好、是否处于飞行模式状态..", Toast.LENGTH_LONG).show();
                return false;
            }else{

                return true;
            }
        }else{
            L.e("terminalIdOld == null");
            return false;
        }

    }

    /**
     * 开始gps
     */
    public void startGps(){

        requestGetCallBeack(CaConfig.url_electronic_fence_json,CaConfig.HttpAutoClose);

        GpsTv.setVisibility(View.GONE);

        reminder = new Reminder(CaConfig.DelayEndTime,MainActivity.this,GpsHandler);//开始10分钟后 计算电子围栏

        reminder.startReminder();


        String jsonList = prefs.getString("jsonList",null);
        if(jsonList != null){
            Gson gson = new Gson();
            CaConfig.electronicFenceJsonBean = gson.fromJson(jsonList,ElectronicFenceJsonBean.class);
        }else{
            HttpIsOk = true;//重置 服务器拉取失败 状态
        }

        //点击开始后  开始按钮不可点击 结束可点击
        Start.setBackgroundResource(R.drawable.btn_on);
        Start.setClickable(false);
        //设置菜单按钮不可点击
        mMenu.setVisibility(View.GONE);
        i = 1;
        //写入时间

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starttime = new Date(System.currentTimeMillis());//获取当前时间
        String startTime = formatter.format(starttime);
        To.putBoolean("isWorking", true);
        To.putString("starttime", startTime);
        To.commit();

        //读取是否工作
        //读取

        WorkingStatus bean =  dbManagerTrust.select();
        if(bean == null){
            //0是结束状态 1是开始
            dbManagerTrust.addData(1);
        }else{
            dbManagerTrust.update(1);
        }


        dbIsWorking = select();
        if(dbIsWorking == 100){
            L.d("db ==  null");
        }else{
            L.d("db dbIsWorking :"+dbIsWorking);
        }

        select();
        if (dbIsWorking == 1)
        {
            time();//开启时间
        }

        GpsHandler.sendEmptyMessageDelayed(CaConfig.delayEnd,CaConfig.DelayEndTime);
//        GpsHandler.sendEmptyMessageDelayed(CaConfig.StartAutoClose,CaConfig.Delay*10);

        promptTv.setVisibility(View.VISIBLE);
        promptTv.setText("未启动电子围栏");
        time();

    }


    /**
     * 获取当前工作状态
     * @return
     */
    public int select(){
        WorkingStatus status =  dbManagerTrust.select();
        if(status == null){
            L.d("db ==  null");
            dbIsWorking = 100;
            boolean is = prefs.getBoolean("isWorking",false);
            if(is){
                return 1;
            }else{
                return 0;//表示没有
            }
        }else{
            L.d("db status:"+status.getName()+"|status:"+status.getStatus());
            dbIsWorking = status.getStatus();
            return status.getStatus();
        }

    }


    /**
     * 刷新时间
     */
    public  void time()
    {
//        isWorking = prefs.getBoolean("isWorking", false);

        if(time == null)
        {
            time = new Time();
        }
        time.Times(hourNumber,minuteNumber,secondsNumber,number,i);
        time.time(dbIsWorking,GpsHandler);
    }



    private void checkIsWorking()
    {
//        if(!isGone){


//        boolean isWorking = prefs.getBoolean("isWorking", false);
        dbIsWorking = select();

        if (dbIsWorking == 1)
        {//工作状态
            Start.setBackgroundResource(R.drawable.btn_on);
            End.setBackgroundResource(R.drawable.btn_off);
            //设置菜单按钮不可见
            mMenu.setVisibility(View.GONE);
            End.setClickable(true);
            Start.setClickable(false);


            //检测版本 如果有更新的版本弹出框提示升级
            L.d("正在工作");





        }
        else
        {//未工作状态
            //设置菜单按钮可见
            mMenu.setVisibility(View.VISIBLE);
            Start.setBackgroundResource(R.drawable.btn_off);
            Start.setClickable(true);
            End.setBackgroundResource(R.drawable.btn_on);
            End.setClickable(false);
            L.d("没有工作");



//            myService.sendInformationCollection(CaConfig.KillAPPCollection,TimeTool.getSystemTimeDate());

//        }
        }
        checkVersions();
    }


    private void checkVersions()
    {

        try {
            localVersion = getVersionName();
            mShowVersion.setText("版本号:"+localVersion);
            CheckVersionTask cv = new CheckVersionTask(MainActivity.this,localVersion);

            new Thread(cv).start();

            Map<String,Object> map = new WeakHashMap<>();
            map.put("driverId",Integer.parseInt(license));
            map.put("version",localVersion);
            // TODO: 2017/7/17
//            post.postUrl(context.getResources().getString(R.string.uir_set_version),CaConfig
//                    .Version,map);
            requestPostCallBeack(CaConfig.uir_set_version,map,CaConfig.Version,CaConfig.noAdd);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getVersionName() throws Exception
    {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = getPackageManager();

        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        return packInfo.versionName;

    }


    @Override
    protected void onResume()
    {

        mShowVersion.setText("版本号:"+localVersion);
        CaConfig.version = Float.parseFloat(localVersion);
        CheckGPS();
        i = 0;
        try
        {
            Thread.sleep(300);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

//        isWorking = prefs.getBoolean("isWorking", false);
        select();
        if (dbIsWorking == 1)
        {
            i = 1;
            ShowTime();
            time();
        } else
        {
//            logger.info("OnResume() and not in working status!");
            L.d("OnResume() and not in working status!");
        }

        promptTv.setVisibility(View.VISIBLE);
        if(fen+(shi * 10) >= CaConfig.DelayEndTime){
            promptTv.setText("未进入电子围栏");
            CaConfig.isOpenAutoClose = true;
            isStartRanging = true;
            L.d("未进入电子围栏");
        }else{
            promptTv.setText("未启动电子围栏");
            CaConfig.isOpenAutoClose = false;
            L.d("未启动电子围栏");
        }


        super.onResume();
    }

    /**
     * 重新刷新主页面计时器 显示的时间
     */
    public void ShowTime()
    {
        show.Show(prefs,day,shi,fen,miao,hourNumber,minuteNumber,secondsNumber,number);
        try {
            Thread.sleep(1000);
            secondsNumber = show.getSecondsNumber();
            number = show.getNumber();
            minuteNumber = show.getMinuteNumber();
            hourNumber = show.getHourNumber();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void CheckGPS()
    {
        if (!UtilTool.isGpsEnabled((LocationManager) getSystemService(Context.LOCATION_SERVICE)))
        {
            Toast.makeText(MainActivity.this, "GSP当前已禁用，请在您的系统设置屏幕启动。", Toast.LENGTH_LONG).show();
            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
            return;
        }
        String serviceName = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(serviceName);
        //判断GPS开关有没有打开
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            L.d("isGone  status:"+isGone);
//            if(!isGone) {
//                boolean isWorking = prefs.getBoolean("isWorking", false);
//
//                L.d("isWorking  status:"+isWorking);
            select();
            if (dbIsWorking != 1) {
                Start.setBackgroundResource(R.drawable.btn_off);
                Start.setClickable(true);
                End.setBackgroundResource(R.drawable.btn_on);
                End.setClickable(false);
            } else {
                Start.setBackgroundResource(R.drawable.btn_on);
                Start.setClickable(false);
                End.setBackgroundResource(R.drawable.btn_off);
                End.setClickable(true);
            }
//            }
        }
        else
        {
            Toast.makeText(MainActivity.this, "GPS关闭了,请打开GPS开关.重新启动应用,可正常工作", Toast.LENGTH_SHORT).show();
            Start.setBackgroundResource(R.drawable.btn_on);
            Start.setClickable(false);
            End.setBackgroundResource(R.drawable.btn_on);
            End.setClickable(false);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            select();
            if (dbIsWorking == 1)
            {
                Toast.makeText(MainActivity.this, "目前处于工作状态,您无法退出...", Toast.LENGTH_LONG).show();
                return true;
            } else {
                finish();
            }
        }
        return false;
    }
}
