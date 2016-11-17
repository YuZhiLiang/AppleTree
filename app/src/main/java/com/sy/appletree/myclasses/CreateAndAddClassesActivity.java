package com.sy.appletree.myclasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.sy.appletree.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAndAddClassesActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.xingZhengClasses)
    RelativeLayout mXingZhengClasses;
    @Bind(R.id.xingQuclasses)
    RelativeLayout mXingQuclasses;
    private String mSchoolID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_add_classes);
        ButterKnife.bind(this);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mSchoolID = intent.getStringExtra("schoolID");
    }

    @OnClick({R.id.base_left, R.id.xingZhengClasses, R.id.xingQuclasses})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.xingZhengClasses:
                //创建行政班
                Intent intent=new Intent(CreateAndAddClassesActivity.this,XZAndXQClassActivity.class);
                intent.putExtra("tag",1);
                intent.putExtra("schoolID", mSchoolID);
                startActivity(intent);
                finish();
                break;
            case R.id.xingQuclasses:
                Intent intent1=new Intent(CreateAndAddClassesActivity.this,XZAndXQClassActivity.class);
                intent1.putExtra("tag",2);
                intent1.putExtra("schoolID", mSchoolID);
                startActivity(intent1);
                finish();
                break;
            default:
                break;
        }
    }
}
