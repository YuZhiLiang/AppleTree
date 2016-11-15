package com.sy.appletree.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sy.appletree.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PresonalInfoActivity extends AppCompatActivity {

    @Bind(R.id.put_name)
    EditText mPutName;
    @Bind(R.id.sex_choose)
    RadioGroup mSexChoose;
    @Bind(R.id.name_email_btn)
    Button mNameEmailBtn;
    private String mPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presonal_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mPhoneNum = intent.getStringExtra("phoneNum");
    }

    @OnClick(R.id.name_email_btn)
    public void onClick() {
        String name = mPutName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ChooseCityActivity2.class);
            intent.putExtra("phoneNum", mPhoneNum);
            intent.putExtra("name", name);
            intent.putExtra("sex", getSex());
            startActivity(intent);
            finish();
        }
    }

    private String getSex() {
        String sex = "1";
        switch (mSexChoose.getCheckedRadioButtonId()) {
            case R.id.man:
                sex = "1";
                break;
            case R.id.woman:
                sex = "2";
                break;
        }
        return sex;
    }
}
