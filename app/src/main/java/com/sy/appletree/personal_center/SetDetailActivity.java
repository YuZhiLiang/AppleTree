package com.sy.appletree.personal_center;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.appletree.R;
import com.sy.appletree.views.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.detail_name_txt)
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


                break;
            case R.id.detail_clear:
                mDetailNameTxt.setText("");
                break;
        }
    }
}
