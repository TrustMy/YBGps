package com.shengyu.ybgps;


import android.webkit.WebView;

import com.shengyu.ybgps.tools.bean.ElectronicFenceJsonBean;
import com.shengyu.ybgps.tools.bean.SdkGpsMessage;

/**
 * Created by Trust on 2017/5/8.
 */
public class CaConfig {
    //tag
    public static final  int Gps = 1111;
    public static final  int Satellite = 1112;//gps信号
    public static final  int Fence = 1113;//电子围栏
    public static final  int CloseFence = 1114;//自动通知工作
    public static final  int PositioningFailedTime = 1115;//gps定位失败后自动计时
    public static final  int GpsDelaySetPrefs = 1116;//每隔 GpsDelay 把起点和终点的信息写入prefs
    public static final  int SelectHistoryData = 1117;//查询/发送历史数据
    public static final  int LoopQuery = 1118;//每间隔一定的时间  查询数据库有没有保存数据有就发送
    public static final  int delayEnd = 1119;//一分钟 结束不能点击
    public static final  int GpsStroke = 1120;//每个一段时间 发送行程数据

    public static final  int StartAutoClose = 1121;//开始计算电子围栏

    public static final  int LogingCollection = 1122;//登录采集
    public static final  int KillAPPCollection = 1123;//杀死app采集
    public static final  int CloseGpsModuleCollection = 1124;//关闭gps开关采集

    public static final  int GpsModuleStatus = 1125;//间隔一定时间 检测gps开关状态

    public static final  int DbStatus = 1126;//更新提示点显示

    public static final  int Version = 1127;//上传version

    public static final  int HttpAutoClose = 1128;//拉取电子围栏

    //配置

    public static short serialNo;//记录保存数据库 点的序列号 以便删除

    public static float version;

    public static int maxSpeed = 2;//超速60

    public static ElectronicFenceJsonBean electronicFenceJsonBean;
    public static String tag;

    public static boolean isOpenAutoClose = false; //是否开始启动自动关闭

    public static  boolean appStatus = false ; //app是否被杀死

    public static int license;//司机id

    public static long terminalId;//设备号

    public static long GpsTime;//gps时间

    public static long StrokeGpsTime;//发送行程时候的时间

    public static SdkGpsMessage sdkStartGpsMessage;
    public static  SdkGpsMessage sdkEndGpsMessage;

    public static String carSerialNumber;


    public static final  long Delay = 1000 *60;//延时
    public static final  long GpsDelay = 1000 * 60;//延时写文件
    public static final  long GpsStrockDelay = 1000 * 5;//延时5s给服务器发送行程

    public static boolean HistoryDateStatus = false;//历史数据发送完毕 是否提示 flse 否 true 是

    public static int autoClose = 30 * 5;//自动关闭的条件  gps是2s一刷 30是一分钟   autoClose 5分钟后停止

    public static final  long DelayLoopQueryTime = 60000  *1;//10分钟查询一次
    public static final  long DelayEndTime = 1000 * 60 * 5;//5分钟后 结束按钮可点击


    public static final  long StartAutoCloseDelay = 1000 * 60 * 10;//延迟10分钟后开始电子围栏



    public static final long OUT_TIME = 1000 * 10;//http 连接超时时间
    public static final int ERROR = 0;//请求回复状态
    public static final int SUCCESS = 1;//请求回复状态


    public static final boolean needAdd = true , noAdd = false;
    public static final int loginTag = 0x0001;//登录

    public static final int typeGPS = 0x9999,typeAlarm = 0x9998,typeTrip= 0x9997;
    public static final String topicSubscription = "changan/yubei/apk/hotfix",topicGps = "data/gps",topicAlarm = "data/alarm",
    topicTrip = "data/trip";


    public static final String login = "http://180.168.194.98:7888/CAWebserver-0.1/registry/reg";
    public static final String updateApp = "http://139.196.229.233/changan/update.xml";
    public static final String url_electronic_fence_json = "http://carlinkcn.com:8080/CAWebserver-0.1/rest/circleFences";
    public static final String uir_set_version = "http://139.196.229.233:8080/CAWebserver-0.1/rest/drivers/updateAppVersion/";



    public static final String SharedPreferencesUser = "UserMsg";
    public static final String SharedPreferencesServer = "Serever";

}

