package com.sy.appletree.login_register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 验证帐号
 */
public class AuthcodeActivity extends AppCompatActivity {

    @Bind(R.id.code_btn)
    Button mCodeBtn;
    @Bind(R.id.register_phone)
    EditText mAuthCode;
    private String mIde;
    private String mPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authcode);
        ButterKnife.bind(this);
        getIntentData();

        downActivity();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        mIde = intent.getStringExtra("IDE");
        mPhoneNum = intent.getStringExtra("phoneNum");
    }

    @OnClick(R.id.code_btn)
    public void onClick() {
        //检查验证码是否为空
        checkAuthCodeIsEquse();
    }

    private void checkAuthCodeIsEquse() {
        String authCode = mAuthCode.getText().toString().trim();
        if (!TextUtils.isEmpty(authCode)) {
            checkEquse(authCode);
        } else {
            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    //检查验证码是否正确
    private void checkEquse(String authCode) {
        if (authCode.equals(mIde)) {
            Intent intent = new Intent(this, PresonalInfoActivity.class);
            intent.putExtra("phoneNum", mPhoneNum);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(BaseApplication.getContext(), "验证码错误", Toast.LENGTH_SHORT).show();
        }
    }


    private void downActivity() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(mFinishReceiver, filter);
    }

    private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

            if ("finish".equals(intent.getAction())) {

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
