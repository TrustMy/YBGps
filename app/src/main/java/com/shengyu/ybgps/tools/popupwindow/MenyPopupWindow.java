package com.shengyu.ybgps.tools.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.shengyu.ybgps.CaConfig;
import com.shengyu.ybgps.R;
import com.shengyu.ybgps.activitys.LoginActivity;

import java.util.List;

/**
 * Created by Trust on 2016/8/29.
 */
public class MenyPopupWindow {

    private View mMenuLayout;
    private ListView mMenuListView;
    private PopupWindow mPopupWindowMenu;

    public MenyPopupWindow(View mMenuLayout, ListView mMenuListView, PopupWindow mPopupWindowMenu) {
        this.mMenuLayout = mMenuLayout;
        this.mMenuListView = mMenuListView;
        this.mPopupWindowMenu = mPopupWindowMenu;
    }

    public void showPopupWidow(final Context context, final ImageView mMenu, final LayoutInflater LayoutInflater, final ImageView mTitle, final RelativeLayout rlTopBar, final List<String> mMenuList, final Handler handler)
    {
        mMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mPopupWindowMenu != null && mPopupWindowMenu.isShowing())
                {
                    mPopupWindowMenu.dismiss();
                }else
                {
                    mMenuLayout =  LayoutInflater.inflate(R.layout.item_popupwindow,null);
                    mMenuListView = (ListView) mMenuLayout.findViewById(R.id.id_popupwindow_list);
                    MeunPopupWindowAdapter adapter = new MeunPopupWindowAdapter(context);
                    mMenuListView.setAdapter(adapter);

                    adapter.setmList(mMenuList);

                    // 创建弹出窗口
                    // 窗口内容为layoutLeft，里面包含一个ListView
                    mPopupWindowMenu = new PopupWindow(mMenuLayout,240,240);
                    ColorDrawable cd = new ColorDrawable(0000);
                    mPopupWindowMenu.setBackgroundDrawable(cd);
                    mPopupWindowMenu.update();
                    mPopupWindowMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    mPopupWindowMenu.setTouchable(true); // 设置popupwindow可点击
                    mPopupWindowMenu.setOutsideTouchable(true); // 设置popupwindow外部可点击
                    mPopupWindowMenu.setFocusable(true); // 获取焦点


                    // 设置popupwindow的位置（相对tvLeft的位置）
                    int topBarHeight = mTitle.getBottom();
                    mPopupWindowMenu.showAsDropDown(rlTopBar, 1080,
                            0);


                    mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            mPopupWindowMenu.dismiss();
                            //To.putBoolean("login", false);
                            //To.commit();
                        switch (position){
                            case 0:
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.putExtra("forceToRegister", true);

                                context.startActivity(intent);
                                break;
                            case 1:
                                CaConfig.HistoryDateStatus = true;
                                handler.sendEmptyMessage(CaConfig.SelectHistoryData);
                                break;
                        }

                            //finish();


                        }


                    });




                    mPopupWindowMenu.setTouchInterceptor(new View.OnTouchListener()
                    {

                        @Override
                        public boolean onTouch(View v, MotionEvent event)
                        {
                            // 如果点击了popupwindow的外部，popupwindow也会消失
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                            {
                                mPopupWindowMenu.dismiss();
                                mMenuList.clear();

                                return true;
                            }
                            return false;
                        }
                    });
                }

            }
        });
    }

}
