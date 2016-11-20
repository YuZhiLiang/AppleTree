package com.sy.appletree.personal_center;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.ToastUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.sy.appletree.views.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.sy.appletree.R.id.detail_name_txt;

/**
 * 个人中心各个页面
 */

public class SetDetailActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.set_title)
    TextView mSetTitle;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(detail_name_txt)
    EditText mDetailNameTxt;
    @Bind(R.id.detail_clear)
    ImageButton mDetailClear;
    @Bind(R.id.detail_name)
    LinearLayout mDetailName;
    @Bind(R.id.detail_sex_man)
    RadioButton mDetailSexMan;
    @Bind(R.id.detail_sex_woman)
    RadioButton mDetailSexWoman;
    @Bind(R.id.detail_sex_shemale)
    RadioButton mDetailSexShemale;
    @Bind(R.id.detail_sex_group)
    RadioGroup mDetailSexGroup;
    @Bind(R.id.detail_sex)
    LinearLayout mDetailSex;
    @Bind(R.id.detail_email_txt)
    EditText mDetailEmailTxt;
    @Bind(R.id.detail_email_clear)
    ImageButton mDetailEmailClear;
    @Bind(R.id.detail_email)
    LinearLayout mDetailEmail;
    @Bind(R.id.detail_phone_txt)
    TextView mDetailPhoneTxt;
    @Bind(R.id.detail_phone)
    LinearLayout mDetailPhone;
    @Bind(R.id.detail_oldpwd_txt)
    EditText mDetailOldpwdTxt;
    @Bind(R.id.detail_mewpwd_txt)
    EditText mDetailMewpwdTxt;
    @Bind(R.id.detail_repetpwd_txt)
    EditText mDetailRepetpwdTxt;
    @Bind(R.id.detail_pwd)
    LinearLayout mDetailPwd;
    @Bind(R.id.detail_about_icon)
    CircleImageView mDetailAboutIcon;
    @Bind(R.id.detail_about)
    LinearLayout mDetailAbout;
    final int CHANGE_NAME = 1;
    final int CHANGE_SEX = 2;
    final int CHANGE_EMAIL = 3;
    final int CHANGE_MOBILE = 4;
    final int CHANGE_PASS_WORD = 5;


    private String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_detail);
        ButterKnife.bind(this);
        TAG = getIntent().getStringExtra("tag");
        setView();
    }

    private void setView() {
        switch (TAG) {
            case "name":
                mSetTitle.setText("名字");
                mDetailName.setVisibility(View.VISIBLE);
                break;
            case "sex":
                mSetTitle.setText("性别");
                mDetailSex.setVisibility(View.VISIBLE);
                break;
            case "email":
                mSetTitle.setText("邮箱");
                mDetailEmail.setVisibility(View.VISIBLE);
                break;
            case "phone":
                mSetTitle.setText("手机号码");
                mDetailPhone.setVisibility(View.VISIBLE);
                mBaseRight.setVisibility(View.INVISIBLE);
                mBaseRight.setClickable(false);
                break;
            case "pwd":
                mSetTitle.setText("更改密码");
                mDetailPwd.setVisibility(View.VISIBLE);
                break;
            case "about":
                mSetTitle.setText("关于评果树");
                mDetailAbout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.base_left, R.id.base_right, R.id.detail_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.base_right:
                //点击保存判断当前设置的意图，之前那个傻逼程序员，服了他，这种写法真他妈第一次见坑爹啊
                switch (TAG) {
                    case "name":
                        setName();
                        break;
                    case "sex":

                        break;
                    case "email":

                        break;
                    case "phone":

                        break;
                    case "pwd":

                        break;
                    case "about":

                        break;
                }

                break;
            case R.id.detail_clear:
                mDetailNameTxt.setText("");
                break;
        }
    }

    private void setName() {
        String name = mDetailNameTxt.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            StringBuffer url = new StringBuffer();
            url.append(AppleTreeUrl.sRootUrl)
                    .append(AppleTreeUrl.ChangeName.PROTOCOL)
                    .append(AppleTreeUrl.ChangeName.PARAMS_NAME)
                    .append(name + "&")
                    .append(AppleTreeUrl.sSession + "=")
                    .append(SPUtils.getSession());
            SetDetailCallBack setDetailCallBack = new SetDetailCallBack(CHANGE_NAME);
            clientService(url, setDetailCallBack);
        } else {
            ToastUtils.toast("请输入姓名");
        }
    }

    private void clientService(StringBuffer url, SetDetailCallBack setDetailCallBack) {
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(setDetailCallBack);
    }

    class SetDetailCallBack extends StringCallback {
        int eventType;

        public SetDetailCallBack(int eventType) {
            this.eventType = eventType;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            switch (eventType) {
                case 1:
                    paraseChangeNameResponse(response, gson);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
            }
        }

        private void paraseChangeNameResponse(String response, Gson gson) {
            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {
                onChangeNameSuccess();
            }else {
                ToastUtils.toast(numberVavlibleBean.getInfo());
            }
        }
    }

    private void onChangeNameSuccess() {
        finish();
    }
}
