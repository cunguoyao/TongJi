package com.linkage.adapter;

import android.app.Activity;
import android.widget.TextView;

import com.linkage.tongji.R;
import com.linkage.tongji.bean.MsgBean;

/**
 * Created by cunguoyao on 2018/3/30.
 */

public class ProvinceDetailAdapter extends BaseAdapter<MsgBean> {

    public ProvinceDetailAdapter(Activity context) {
        super(context);
    }

    @Override
    protected int getItemLayoutId(int itemViewType) {
        return R.layout.adapter_msg_centre_item;
    }

    @Override
    protected void handleItem(int itemViewType, int position, MsgBean item, ViewHolder holder, boolean reused) {
        holder.get(R.id.item_title, TextView.class).setText(item.getTitle());
        holder.get(R.id.item_content, TextView.class).setText(item.getContent());
        holder.get(R.id.date_text, TextView.class).setText(item.getTime());
    }

}
