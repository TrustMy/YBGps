package com.shengyu.ybgps.sy.commn;

/**
 * Created by jiayang on 2015/8/27.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;




public class BootCompleteReceiver extends BroadcastReceiver {

//    private static Logger logger = LoggerFactory.getLogger(BootCompleteReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
//            logger.info("Boot completed received!!!!!!!");
//            context.startService(new Intent(context, MyService.class));
        }
    }
}

