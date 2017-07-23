package com.shengyu.ybgps.tools;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Trust on 2017/7/17.
 */

public class TrustCheckConfig {

    /**
     * 判断手机是否是飞行模式
     *
     * @param context
     * @return
     */
    public static boolean getAirplaneMode(Context context)
    {
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0);
        return (isAirplaneMode == 1) ? true : false;
    }


}
