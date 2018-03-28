package com.linkage.tongji;

import android.os.Bundle;
import android.widget.TextView;

import com.linkage.tongji.app.BaseApplication;
import com.linkage.utils.StateBarTranslucentUtils;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by cunguoyao on 2018/3/27.
 */

public class BaseActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(false);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        //mSwipeBackLayout.setEdgeSize(200);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        StateBarTranslucentUtils.setStatusBarFontDark(this, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!BaseApplication.getInstance().getUnSetStateBarColorPage().contains(this.getClass().getName())) {
            StateBarTranslucentUtils.setStateBarColor(this, R.color.colorMain);
        }
    }

    public void setTitle(String title) {
        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText(title);
    }
}
