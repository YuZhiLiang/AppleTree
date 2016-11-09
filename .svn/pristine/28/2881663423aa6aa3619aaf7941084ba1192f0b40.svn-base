package com.sy.appletree.login_register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.sy.appletree.R;
import com.sy.appletree.homepage.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册成功
 */
public class RegisterSuccessActivity extends AppCompatActivity {

    @Bind(R.id.success_btn)
    Button mCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
        downActivity();
    }

    @OnClick(R.id.success_btn)
    public void onClick() {
        Intent intent=new Intent(RegisterSuccessActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void downActivity() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(mFinishReceiver, filter);
    }
    private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

            if("finish".equals(intent.getAction())) {

                Log.e("#########", "I am " + getLocalClassName()

                        + ",now finishing myself...");

                finish();

            }

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFinishReceiver);
    }
}
