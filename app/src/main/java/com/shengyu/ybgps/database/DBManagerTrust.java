package com.shengyu.ybgps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.activitys.BaseActivty;
import com.shengyu.ybgps.tools.L;
import com.shengyu.ybgps.tools.bean.TripMsg;
import com.shengyu.ybgps.tools.bean.TypeAlarmBean;
import com.shengyu.ybgps.tools.bean.TypeBean;
import com.shengyu.ybgps.tools.bean.TypeGpsBean;
import com.shengyu.ybgps.tools.bean.TypeTripBean;
import com.shengyu.ybgps.tools.bean.WorkingStatus;


/**
 * Created by Trust on 2017/6/7.
 */

public class DBManagerTrust <T extends TypeBean> {
    private SQLiteDatabase dbWrit;
    private SQLiteDatabase dbRead;
    private ContentValues contentValues;
    private DBHelperTrust dbHelper;
    private T bean;
    private String SerialNo;
    private int rawId;
    public DBManagerTrust(Context context) {
        dbHelper = new DBHelperTrust(context);
        dbWrit = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();
        contentValues = new ContentValues();
    }

    public void addData(int status ){
        contentValues.put("name","IsWorking");
        contentValues.put("status",status);
        dbWrit.insert("Trust",null,contentValues);
        L.d("add status:"+status);
        contentValues.clear();
    }


    public WorkingStatus select(){
        Cursor cursor = dbWrit.query("Trust",null,null,null,null,null,null);
        WorkingStatus bean  = null;
        if(cursor.moveToFirst()){
            do {

               String name = cursor.getString(cursor.getColumnIndex("name"));
                int status =    cursor.getInt(cursor.getColumnIndex("status"));

                Log.d("lhh", "select: name:"+name+"|status："+status);
                L.d("select status:"+status);
                bean = new WorkingStatus(name,status);

            }while (cursor.moveToNext());
        }
        cursor.close();


            return bean;


    }


    public void update(int status){
        contentValues.put("status",status);
        dbWrit.update("Trust",contentValues,"name = ?",
                new String[]{"IsWorking"});
        contentValues.clear();
        L.d("update status:"+status);

    }




    public void addDataTrip(String msg){
        contentValues.put("trip","trip");
        contentValues.put("message",msg);
        dbWrit.insert("Trip",null,contentValues);
        L.d("addDataTrip:"+msg);
        contentValues.clear();
    }



    public TripMsg selectTrip(){
        Cursor cursor = dbWrit.query("Trip",null,null,null,null,null,null);
        TripMsg bean  = null;
        if(cursor.moveToFirst()){
            do {

                String trip = cursor.getString(cursor.getColumnIndex("trip"));
                String msg  =   cursor.getString(cursor.getColumnIndex("message"));

                bean = new TripMsg(trip,msg);

            }while (cursor.moveToNext());
        }
        cursor.close();
        if(bean == null){
            L.d("bean == null");
        }else{
            L.d("selectTrip:"+bean.getMessage());
        }

        return bean;


    }



    public void updateTrip(String msg){
        contentValues.put("message",msg);
        dbWrit.update("Trip",contentValues,"trip = ?",
                new String[]{"trip"});
        L.d("updateTrip:"+msg);
        contentValues.clear();

    }


    public void deleteTrip() {
        dbWrit.delete("Trip","trip = ?",new String[]{"trip"});
    }


    public void addGps(int type,long serialNo,String message){
        contentValues.put("type",type);
        contentValues.put("serialNo",serialNo+"");
        contentValues.put("message",message);
        dbWrit.insert("Gps",null,contentValues);
        contentValues.clear();
    }

    public T selectFirst(){
        Gson gson = new Gson();
        String table = "Gps";
        String[] columns = new String[] {"timeStamprawId", "type","serialNo", "message"};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = "1";

        Cursor c = dbWrit.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if ( c.getCount() == 0 ) {
            c.close();
            return null;
        }
        c.moveToFirst();
        int type = c.getInt(c.getColumnIndex("type"));
        rawId = c.getInt(c.getColumnIndex("timeStamprawId"));
        SerialNo = c.getString(c.getColumnIndex("serialNo"));
        L.d(" 主键:"+rawId+"|SerialNo:"+SerialNo);
        switch (type){
            case CaConfig.typeGPS:

                L.d("查出来是  Gps:"+c.getString(c.getColumnIndex("message")));
                bean = (T) gson.fromJson(c.getString(c.getColumnIndex("message")), TypeGpsBean.class);
                break;
            case CaConfig.typeAlarm:
                bean = (T) gson.fromJson(c.getString(c.getColumnIndex("message")), TypeAlarmBean.class);
                L.d("查出来是  type:"+type+"   typeAlarm:"+c.getString(c.getColumnIndex("message")));
                break;
            case CaConfig.typeTrip:
//                L.d("trip 主键:"+c.getInt());
                L.d("查出来是  type:"+type+"   typeTrip:"+c.getString(c.getColumnIndex("message")));
                bean = (T) gson.fromJson(c.getString(c.getColumnIndex("message")), TypeTripBean.class);
                break;
        }
        bean.setTypes(type);
        bean.setSerialNos(SerialNo);
        bean.setRawId(rawId);
        L.d("typeBean 序列号:"+SerialNo+"|rawId:"+rawId);
        return bean;
    }

    /**
     * 通过ID删除一行记录
     */
    public void deleteGps(int rawId,String serialNo) {
//        dbWrit.execSQL("delete from Gps  where serialNo = " + serialNo);
        dbWrit.execSQL("delete from " + "Gps" + " where timeStamprawId = " +rawId + " and " +
                "serialNo = "+serialNo);
        L.d("删除:"+serialNo+"|timeStamprawId:"+rawId);

//        selectAllGps();
    }


    public void selectAllGps(){
        Cursor cursor = dbWrit.query("Gps",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                L.d("select 主键 :"+cursor.getInt(cursor.getColumnIndex("timeStamprawId"))+
                "|序列号:"+cursor.getInt(cursor.getColumnIndex("serialNo")));
               L.d("type:"+cursor.getInt(cursor.getColumnIndex("type"))+"|serialNo:"+
               cursor.getLong(cursor.getColumnIndex("serialNo"))+"|msg:"+
               cursor.getString(cursor.getColumnIndex("message")));
            }while (cursor.moveToNext());
        }
        cursor.close();

    }



    public String selectFirstDate(long serialNo){
        String selection = "serialNo= ?" ;
        String[] selectionArgs = new  String[]{ serialNo+"" };
        String termId = null;
        if(serialNo!=0){
            Cursor cursor = dbWrit.query("Gps",null,selection,selectionArgs,null,null,null);
            if(cursor.moveToFirst()){
                do {
                    termId = cursor.getString(cursor.getColumnIndex("termId"));
                }while (cursor.moveToNext());
            }
            cursor.close();
            return termId;
        }else{
            return null;
        }
    }

    public void clearRawId(){
        dbWrit.execSQL("DELETE FROM sqlite_sequence WHERE name = 'Gps'");
    }


}
