package com.linkage.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkage.tongji.R;

/**
 * Created by cunguoyao on 2017/9/16.
 */

public class LoadingDialog extends Dialog {
    private static final int CHANGE_TITLE_WHAT = 1;
    private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private static final int MAX_SUFFIX_NUMBER = 3;
    private static final char SUFFIX = '.';

    private ImageView iv_route;
    private TextView tv;
    private TextView tv_point;
    private RotateAnimation mAnim;
    private boolean cancelable = true;
    private Context context;
    /**
     * 定义一个handler,载入就发送一个即时消息，让原点+1,继而在每隔300毫秒发送一个延迟消息，来添加+1
     */
    private Handler handler = new Handler(){
        //正在载入的原点数量
        private int num = 0;

        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num ++;
                for (int i = 0;i < num;i++) {
                    builder.append(SUFFIX);
                }
                tv_point.setText(builder.toString());
                if (isShowing()) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
                } else {
                    num = 0;
                }
            }
        };
    };

    public LoadingDialog(Context context) {
        super(context, R.style.Dialog_bocop);
        this.context = context;
        init();
    }

    @SuppressLint("ResourceType")
    private void init() {
        View contentView = View.inflate(context, R.layout.view_loading_dialog, null);
        setContentView(contentView);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelable) {
                    dismiss();
                }
            }
        });
        iv_route = (ImageView) findViewById(R.id.iv_route);
        tv = (TextView) findViewById(R.id.tv);
        tv_point = (TextView) findViewById(R.id.tv_point);
        /**动画初始化*/
        initAnim();
        //背景暗色
        getWindow().setWindowAnimations(R.anim.alpha_in);
    }


    private void initAnim() {
        mAnim = new RotateAnimation(360, 0, Animation.RESTART, 0.5f, Animation.RESTART,0.5f);
        mAnim.setDuration(2000);
        // 设置动画反复次数
        mAnim.setRepeatCount(Animation.INFINITE);
        //动画反复的模式--运行完第一次动画之后。回到动画開始然后运行第二次动画
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setStartTime(Animation.START_ON_FIRST_FRAME);
    }

    @Override
    public void show() {
        iv_route.startAnimation(mAnim);
        handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
        super.show();
    }

    @Override
    public void dismiss() {
        mAnim.cancel();
        super.dismiss();
    }


    @Override
    public void setCancelable(boolean flag) {
        cancelable = flag;
        super.setCancelable(flag);
    }
}
