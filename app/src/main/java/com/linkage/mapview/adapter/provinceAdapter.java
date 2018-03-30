package com.linkage.mapview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.linkage.tongji.R;
import com.linkage.tongji.bean.IndexReport;

import java.util.ArrayList;

/**
 * Created by Vmmet on 2016/10/10.
 */
public class provinceAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<IndexReport> list;
    private int position;

    public provinceAdapter(Context context, ArrayList<IndexReport> list){
        this.context=context;
        this.list=list;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public IndexReport getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        IndexReport item = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.listview_province_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ll = (LinearLayout) view.findViewById(R.id.ll);
            viewHolder.province_name = (TextView) view.findViewById(R.id.province_name);
            viewHolder.province_user_total = (TextView) view.findViewById(R.id.province_user_total);
            viewHolder.province_user_increase = (TextView) view.findViewById(R.id.province_user_increase);
            viewHolder.update_date = (TextView) view.findViewById(R.id.update_date);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.province_name.setText(item.getProvinceName());
        viewHolder.province_user_total.setText(item.getUserTotal()+"");
        String increase;
        if(item.getUserIncrease() == 0) {
            increase = "";
        }else {
            increase = item.getUserIncrease()+"";
        }
        viewHolder.province_user_increase.setText(increase);
        viewHolder.update_date.setText(item.getCreateTime());
        if (i==position){
            viewHolder.province_name.setTextColor(Color.RED);
        }else{
            viewHolder.province_name.setTextColor(Color.BLACK);
        }
        return view;
    }
    private class ViewHolder {
        TextView province_name,province_user_total,
                province_user_increase,update_date;
        LinearLayout ll;
    }
}
