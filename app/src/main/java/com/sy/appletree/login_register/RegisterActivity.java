package com.sy.appletree.login_register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.http_about_utils.CheckForAllUtils;
import com.sy.appletree.utils.http_about_utils.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.register_btn)
    Button mRegisterBtn;
    @Bind(R.id.register_phone)
    EditText mRegister_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        downActivity();
    }


    @OnClick(R.id.register_btn)
    public void onClick() {
        //发送验证码
        String phoneNum = mRegister_phone.getText().toString().trim();
        boolean mobileNO = CheckForAllUtils.isMobileNO(phoneNum);
        //验证手机号码是否可用
        if (mobileNO) {
            checkForRegister(phoneNum);
        } else {
            //提示手机号码错误
            mRegister_phone.setError("请输入正确的手机号码");
        }
    }

    //检查手机号码是否被注册
    private void checkForRegister(final String phoneNum) {
        Map<String, Object> params = new HashMap<>();
        params.put(AppleTreeUrl.MobileCheck.PARAMS_MOBILE, phoneNum);
        String url = AppleTreeUrl.sRootUrl + AppleTreeUrl.MobileCheck.PROTOCOL
                + HttpUtils.getUrlParamsByMap(params);

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
                        onGetCheckCode(numberVavlibleBean, phoneNum);
                    }
                });
    }

    private void onGetCheckCode(NumberVavlibleBean numberVavlibleBean, String phoneNum) {
        String status = numberVavlibleBean.getStatus();
        if (status.equals("y")) {
            //可以注册，发送验证码，打开下一个页面
            sendValCode(phoneNum);
        } else {
            //号码被占用
            mRegister_phone.setError("号码已被注册或不可用");
        }
    }

    //发送验证码
    private void sendValCode(final String phoneNum) {

        Map<String, Object> params = new HashMap<>();
        params.put(AppleTreeUrl.SendValCode.PARAMS_MOBILE, phoneNum);
        String url = AppleTreeUrl.sRootUrl + AppleTreeUrl.SendValCode.PROTOCOL
                + HttpUtils.getUrlParamsByMap(params);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "验证码发送失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Gson gson = new Gson();
                        //检查号码是否被注册的Bean和发送验证码的Bean一样，这里拿来复用了
                        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
                        if (numberVavlibleBean.getStatus().equals("y")) {
                            Toast.makeText(getApplicationContext(), numberVavlibleBean.getData().toString() , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, AuthcodeActivity.class);
                            intent.putExtra("IDE", numberVavlibleBean.getData().toString());
                            intent.putExtra("phoneNum", phoneNum);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "验证码发送失败", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFinishReceiver);
    }
}
