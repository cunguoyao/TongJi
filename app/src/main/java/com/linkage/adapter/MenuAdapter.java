package com.linkage.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkage.tongji.R;
import com.linkage.tongji.WebViewActivity;
import com.linkage.tongji.bean.MenuBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cunguoyao on 2018/3/27.
 */

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<MenuBean> menuList;

    public MenuAdapter(Context context, List<MenuBean> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        int num = menuList == null ? 0 : menuList.size();
        Log.e("---num--" , ""+num);
        return num;
    }

    @Override
    public MenuBean getItem(int i) {
        return menuList == null ? null : menuList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return menuList == null ? 0 : menuList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("---getView--" , ""+position);
        MenuHolder holder = null;
        MenuBean item = getItem(position);
        if (convertView == null) {
            holder = new MenuHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_menu, parent, false);
            holder.imageView = convertView.findViewById(R.id.iv_item);
            holder.textView = convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        }else {
            holder = (MenuHolder) convertView.getTag();
        }
        holder.textView.setText(item.getIconMsg());
        Picasso.with(context).load(item.getIconUrl()).placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class MenuHolder {
        ImageView imageView;
        TextView textView;
    }
}
