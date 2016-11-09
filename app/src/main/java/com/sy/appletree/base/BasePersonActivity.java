package com.sy.appletree.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sy.appletree.R;

/**
 * 模板页面，用来copy布局使用
 */
public class BasePersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }
}
