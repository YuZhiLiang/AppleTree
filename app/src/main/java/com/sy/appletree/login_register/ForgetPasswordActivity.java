package com.sy.appletree.login_register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sy.appletree.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends AppCompatActivity {

    @Bind(R.id.forget_phone)
    EditText mForgetPhone;
    @Bind(R.id.forget_code_edit)
    EditText mForgetCodeEdit;
    @Bind(R.id.forget_code)
    Button mForgetCode;
    @Bind(R.id.forget_btn)
    Button mForgetBtn;

    private TimeCount time;
    protected static final int START_COUNT = 2;

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case START_COUNT:
                    time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
                    time.start();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        downActivity();
    }

    @OnClick({R.id.forget_code, R.id.forget_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_code:
                getData();
                break;
            case R.id.forget_btn:
                //调用发短信接口，成功就跳转。
                Intent intent=new Intent(ForgetPasswordActivity.this,ReLoginActivity.class);

                startActivity(intent);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getData() {
        boolean empty = TextUtils.isEmpty(mForgetPhone.getText().toString().trim());
        if (empty){
            Toast.makeText(ForgetPasswordActivity.this,"请输入您的手机号",Toast.LENGTH_SHORT).show();
            return;
        }else if (!(mForgetPhone.getText().toString().trim().length() == 11)){
            Toast.makeText(ForgetPasswordActivity.this,"您输入的手机号码的位数错误",Toast.LENGTH_SHORT).show();
            return;
        }
        //做网络请求

        Message msg = Message.obtain();
        msg.what = START_COUNT;
        handler.sendMessage(msg);

    }


    /**
     * 发送验证码计时器
     *
     * @author wdp
     *
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发

            mForgetCode.setText("重新发送");
            mForgetCode.setClickable(true);
            mForgetCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mForgetCode.setClickable(false);
            mForgetCode.setEnabled(false);
            mForgetCode.setText(millisUntilFinished / 1000 + "秒");
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
