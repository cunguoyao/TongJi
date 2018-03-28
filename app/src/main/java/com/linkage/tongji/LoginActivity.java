package com.linkage.tongji;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkage.mapview.MapActivity;
import com.linkage.tongji.app.Urls;
import com.linkage.utils.C;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = LoginActivity.class.getName();

	private TextView mBtnLogin;

	private View progress;

	private View mInputLayout;

	private float mWidth, mHeight;

	private LinearLayout mName, mPsw;

	private EditText tv_user,tv_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setSwipeBackEnable(false);
//		StatusBarUtil.setColor(LoginActivity.this, getResources().getColor(R.color.colorLogin),0);
//		StatusBarUtil.setTranslucent(LoginActivity.this,255);
//		StatusBarUtil.setTransparent(LoginActivity.this);
		initView();
	}
	public void hideKeyboard(IBinder token) {
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		mInputLayout.setVisibility(View.VISIBLE);
		mName.setVisibility(View.VISIBLE);
		mPsw.setVisibility(View.VISIBLE);
		mBtnLogin.setVisibility(View.VISIBLE);


	}

	private void initView() {
		mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
		progress = findViewById(R.id.layout_progress);
		mInputLayout = findViewById(R.id.input_layout);
		mName = (LinearLayout) findViewById(R.id.input_layout_name);
		mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
		tv_user = (EditText) findViewById(R.id.tv_user);
		tv_pwd= (EditText) findViewById(R.id.tv_pwd);


		mBtnLogin.setOnClickListener(this);

		tv_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
												  KeyEvent event) {
						if (actionId == EditorInfo.IME_NULL) {
							hideKeyboard(mPsw.getWindowToken());
							return true;
						}
						return false;
					}
				});

	}



	@Override
	public void onClick(View v) {
		mBtnLogin.setVisibility(View.GONE);
		hideKeyboard(mPsw.getWindowToken());
		mWidth = mBtnLogin.getMeasuredWidth();
		mHeight = mBtnLogin.getMeasuredHeight();



		inputAnimator(mInputLayout, mWidth, mHeight);



	}



	private void inputAnimator(final View view, float w, float h) {

	

		ValueAnimator animator = ValueAnimator.ofFloat(0, w);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
				params.leftMargin = (int) value;
				params.rightMargin = (int) value;
				view.setLayoutParams(params);
			}
		});
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.2f);
		set.setDuration(300);
		set.setInterpolator(new AccelerateDecelerateInterpolator());
		set.playTogether(animator, animator2);
		set.start();
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {

				progress.setVisibility(View.VISIBLE);
				progressAnimator(progress);
				mInputLayout.setVisibility(View.INVISIBLE);
				postLogin(tv_user.getText().toString(), tv_pwd.getText().toString());
				new Handler().postDelayed(new Runnable() {
					public void run() {
//						recovery();
					}
				}, 2000);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void progressAnimator(final View view) {
		PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 0.5f);
		PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 0.5f);
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
		animator3.setDuration(1000);
		animator3.setInterpolator(new JellyInterpolator());
		animator3.start();

	}

	/**
	 * �ָ���ʼ״̬
	 */
	private void recovery() {
		progress.setVisibility(View.GONE);
//		mInputLayout.setVisibility(View.VISIBLE);
//		mName.setVisibility(View.VISIBLE);
//		mPsw.setVisibility(View.VISIBLE);
//		mBtnLogin.setVisibility(View.VISIBLE);

		ViewGroup.MarginLayoutParams params = (MarginLayoutParams) mInputLayout.getLayoutParams();
		params.leftMargin = 0;
		params.rightMargin = 0;
		mInputLayout.setLayoutParams(params);
		
		
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
		animator2.setDuration(500);
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());
		animator2.start();

		Intent intent_sl = new Intent();
//		intent_sl.setClass(LoginActivity.this, MainActivity.class);
		intent_sl.setClass(LoginActivity.this, MapActivity.class);
		startActivity(intent_sl);

	}


	private void postLogin(String username, String password) {
		Map<String, String> params = new HashMap<>();
		params.put("user", username);
		params.put("pwd", C.md5(password));
		NetRequest.postJsonRequest(Urls.login, params, TAG, new NetRequest.DataCallBack() {
			@Override
			public void requestSuccess(String result) throws Exception {
				LogUtils.d("--NetRequest--success--", result);
				handler.sendEmptyMessage(0);
			}

			@Override
			public void requestFailure(Request request, IOException e) {
				LogUtils.d("--NetRequest--fail--", "");
				handler.sendEmptyMessage(0);
			}
		});
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
				recovery();
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}


        }

    };

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetRequest.cancelRequest(TAG);
	}
}
