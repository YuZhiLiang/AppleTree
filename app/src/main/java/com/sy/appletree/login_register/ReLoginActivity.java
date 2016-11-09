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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重新登录
 */
public class ReLoginActivity extends AppCompatActivity {

    @Bind(R.id.relogin_btn)
    Button mReloginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_login);
        ButterKnife.bind(this);
        downActivity();
    }

    @OnClick(R.id.relogin_btn)
    public void onClick() {
        Intent intent=new Intent(ReLoginActivity.this,LoginAvtivity.class);
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
