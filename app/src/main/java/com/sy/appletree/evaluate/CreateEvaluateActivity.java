package com.sy.appletree.evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sy.appletree.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新建指标页面
 */

public class CreateEvaluateActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.eva_choose_icon)
    ImageView mEvaChooseIcon;
    @Bind(R.id.eva_name)
    EditText mEvaName;
    @Bind(R.id.eva_describe)
    EditText mEvaDescribe;
    @Bind(R.id.eva_fenzhi)
    EditText mEvaFenzhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_evaluate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.base_left, R.id.base_right, R.id.eva_choose_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.base_right:
            //TODO 将添加好的评价标准保存

                //
                finish();
                break;
            case R.id.eva_choose_icon:
                Intent intent = new Intent(CreateEvaluateActivity.this, ChooseEvaluateIconActivity.class);

                startActivityForResult(intent, 100);

                break;
        }
    }
}
