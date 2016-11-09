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

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.ValAuthCodeBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.http_about_utils.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 验证帐号
 */
public class AuthcodeActivity extends AppCompatActivity {

    @Bind(R.id.code_btn)
    Button mCodeBtn;
    @Bind(R.id.register_phone)
    EditText mAuthCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authcode);
        ButterKnife.bind(this);


        downActivity();

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
        //TODO 检查验证码
        Map<String, Object> params = new HashMap<>();
        params.put(AppleTreeUrl.ValCode.PARAMS_VAI_CODE, authCode);
        String url = AppleTreeUrl.sRootUrl + AppleTreeUrl.ValCode.PROTOCOL
                + HttpUtils.getUrlParamsByMap(params);

        Log.e("带上验证码访问的Url", url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络链接出错了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ValAuthCodeBean valAuthCodeBean = gson.fromJson(response, ValAuthCodeBean.class);
                        Log.e("拿到网络响应了", valAuthCodeBean.getStatus() + valAuthCodeBean.getData() + valAuthCodeBean.getInfo());
                    }
                });
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
