package com.linkage.tongji;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by cunguoyao on 2018/3/27.
 */

public class BaseActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setTitle(String title) {
        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText(title);
    }
}
