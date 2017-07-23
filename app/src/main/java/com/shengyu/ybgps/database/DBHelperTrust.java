package com.shengyu.ybgps.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jiayang on 2015/9/15.
 */
public class DBHelperTrust extends SQLiteOpenHelper {

    private static final String TableName = "Trust";

    private static final String TRIP = "Trip";

    private static final String TableGps = "Gps";
    public static final String TIMESTAMP = "timeStamp";

    private static final String CREATE_TRUST = "create table "+TableName+"(" +
            "id integer primary key autoincrement," +
            "name text," +
            "status INTEGER)";

    private static final String CREATE_TRIP = "create table "+TRIP+"(" +
            "id integer primary key autoincrement," +
            "trip text," +
            "message text)";


    private static final String CREATE_GPS = "create table " + TableGps +
            " (" + TIMESTAMP +
            "rawId integer primary key autoincrement," +

            "type" + " integer, " + "serialNo" + " text, " + "message" + " text)";


    public DBHelperTrust(Context context) {
        super(context, "Trust.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TRUST);
        sqLiteDatabase.execSQL(CREATE_TRIP);
        sqLiteDatabase.execSQL(CREATE_GPS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
