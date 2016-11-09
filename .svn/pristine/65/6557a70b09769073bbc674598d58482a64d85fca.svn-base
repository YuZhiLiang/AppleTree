package com.sy.appletree.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.sy.appletree.R;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.login_register.LoginAvtivity;
import com.sy.appletree.utils.Const;
import com.sy.appletree.utils.SharePreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @Bind(R.id.splash_img)
    ImageView mSplashImg;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Intent intent1 = new Intent(SplashScreenActivity.this, LoginAvtivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        toNextActivity();
    }


    public void toNextActivity() {
        boolean is_first = (Boolean) SharePreferenceUtils.getParam(getApplicationContext(), "is_first", false);
        if (!is_first) {
            //第一次启动
            mHandler.sendEmptyMessageDelayed(2, Const.SPLASH_DELAY_TIME);

        } else {
            //不是第一次启动
            mHandler.sendEmptyMessageDelayed(1, Const.SPLASH_DELAY_TIME);
        }

    }

    @Override
    protected void onDestroy() {
        if (null != mHandler) {
            mHandler.removeMessages(2);
            mHandler.removeMessages(1);
            mHandler = null;
            Log.d("tag", "release Handler success");
        }
        super.onDestroy();
    }
}
