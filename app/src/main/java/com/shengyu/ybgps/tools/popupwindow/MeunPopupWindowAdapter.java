package com.shengyu.ybgps.tools.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.shengyu.ybgps.R;

import java.util.List;

/**
 * Created by Trust on 2016/8/5.
 */
public class MeunPopupWindowAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public MeunPopupWindowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview,null);
            holder = new ViewHolder();
            holder.tvData = (TextView) convertView.findViewById(R.id.list_item_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvData.setText(mList.get(position));

        return convertView;
    }
    class ViewHolder {
        TextView tvData;
    }



}
