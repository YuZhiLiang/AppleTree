package com.sy.appletree.login_register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.http_about_utils.CheckForAllUtils;
import com.sy.appletree.utils.http_about_utils.HttpUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginAvtivity extends AppCompatActivity {

    @Bind(R.id.login_phone)
    EditText mLoginPhone;
    @Bind(R.id.login_pwd)
    EditText mLoginPwd;
    @Bind(R.id.login_btn)
    Button mLoginBtn;
    @Bind(R.id.login_forget)
    TextView mLoginForget;
    @Bind(R.id.login_to_register)
    TextView mLoginToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_avtivity);
        ButterKnife.bind(this);
        downActivity();
    }

    @OnClick({R.id.login_btn, R.id.login_forget, R.id.login_to_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                subSpace();
                break;
            case R.id.login_forget:
                Intent intent1 = new Intent(LoginAvtivity.this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.login_to_register:
                Intent intent = new Intent(LoginAvtivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void subSpace() {
        String user = mLoginPhone.getText().toString().trim();
        String pass = mLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            toast("请输入密码");
            return;
        }
        loging(user, pass);
    }

    private void loging(String user, String pass) {
              String baseUrl = AppleTreeUrl.sRootUrl + AppleTreeUrl.Loging.PROTOCOL;
        Map<String, Object> params = new HashMap<>();
        String MD5Password = CheckForAllUtils.getMD5(pass);//MD5
        params.put(AppleTreeUrl.Loging.PARAMS_USERNAME, user);
        params.put(AppleTreeUrl.Loging.PARAMS_PASSWORD, MD5Password);//MD5
//        params.put(AppleTreeUrl.Loging.PARAMS_PASSWORD, pass);
        String url = baseUrl + HttpUtils.getUrlParamsByMap(params);

        Log.e("info", url);

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "登录失败ing", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        //bean类一样的 拿来复用
                        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
                        if (numberVavlibleBean.getStatus().equals("y")) {
                            //存储Session

                            SPUtils.putSession(numberVavlibleBean.getData().toString());
                            Intent intent2 = new Intent(LoginAvtivity.this, MainActivity.class);
                            startActivity(intent2);
                            toast("登录成功");

                        } else {
                            toast(numberVavlibleBean.getInfo());
                        }

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

    void toast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFinishReceiver);
    }
}
